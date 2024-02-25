package com.pipedev.twelvedata.connection;

import com.fasterxml.jackson.databind.JsonNode;
import com.pipedev.twelvedata.model.data.SymbolQuote;
import com.pipedev.twelvedata.model.data.SymbolSubscription;
import com.pipedev.twelvedata.model.enums.MessageActionEnum;
import com.pipedev.twelvedata.model.enums.ServerEventEnum;
import com.pipedev.twelvedata.model.utils.JsonUtils;
import com.pipedev.twelvedata.model.websocket.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class WSConnection {

    //wss://ws.twelvedata.com/v1/quotes/price?apikey=e39059603c924781844e66ddc256cf34
    private final String url;
    private final BiConsumer<Object, ServerEventEnum> messageListener;
    private final Consumer<Boolean> connectionListener;
    private final BiConsumer<Throwable, String> onErrorListener;

    private WebSocketSession session;
    private Timer timer;

    public WSConnection(String wsUrl, String apiKey, BiConsumer<Object, ServerEventEnum> messageListener, Consumer<Boolean> connectionListener, BiConsumer<Throwable, String> onErrorListener) {
        this.messageListener = messageListener;
        this.connectionListener = connectionListener;
        this.onErrorListener = onErrorListener;
        this.url = String.format("%s/quotes/price?apikey=%s", wsUrl, apiKey);
    }


    public void connect() {
        this.timer = new Timer();
        WebSocketClient client = new StandardWebSocketClient();
        var session = client.execute(new WSHandler(this::onMessage, this::onSession), this.url);
        session.join();
    }

    public void sendMessage(SendMessage message) {
        try {
            var messageJson = JsonUtils.toString(message);
            log.debug("Sending message: {}", messageJson);
            session.sendMessage(new TextMessage(messageJson));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            session.close();
            if(timer != null)
                timer.cancel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restart() {
        close();
        connect();
    }

    private void onMessage(String message) {
        try {
            log.debug("Received message: {}", message);
            JsonNode messageNode = JsonUtils.readValue(message, JsonNode.class);
            if(messageNode.has("status") && messageNode.get("status").asText().equals("error")) {
                throw new RuntimeException(handleErrorMessages(messageNode));
            }
            ServerEventEnum event = ServerEventEnum.fromValue(messageNode.get("event").textValue());
            switch (event) {
                case CONNECTION:
                case HEARTBEAT:
                    break;
                case PRICE:
                    messageListener.accept(JsonUtils.readValue(message, SymbolQuote.class), event);
                    break;
                case SUBSCRIBE_STATUS:
                    messageListener.accept(JsonUtils.readListValue(messageNode.get("success").toString(), SymbolSubscription.class), event);
                    break;
                default:
                    throw new RuntimeException("Unknown event: " + event);
            }
        } catch (Exception e) {
            onErrorListener.accept(e, message);
        }
    }

    private void onSession(WebSocketSession handler, Boolean isConnected) {
        if(isConnected) {
            this.session = handler;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    heartbeat();
                }
            }, TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(10));

        }else {
            this.session = null;
            timer.cancel();
            timer = null;
            CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(()->{
                log.info("Reconnecting...");
                this.connect();
            });
        }
        connectionListener.accept(isConnected);
    }

    private String handleErrorMessages(JsonNode messageNode) {
       if(messageNode.has("messages")) {
           return String.join(",", JsonUtils.readValue(messageNode.get("messages").toString(), String[].class));
       }
       return "Unknown error";
    }


    private void heartbeat() {
        SendMessage message = new SendMessage();
        message.setAction(MessageActionEnum.HEARTBEAT);
        sendMessage(message);
    }



}
