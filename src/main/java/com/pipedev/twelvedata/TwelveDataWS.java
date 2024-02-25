package com.pipedev.twelvedata;

import com.pipedev.twelvedata.connection.WSConnection;
import com.pipedev.twelvedata.model.data.SymbolQuote;
import com.pipedev.twelvedata.model.data.SymbolSubscription;
import com.pipedev.twelvedata.model.enums.MessageActionEnum;
import com.pipedev.twelvedata.model.enums.ServerEventEnum;
import com.pipedev.twelvedata.model.listener.TwelveDataListener;
import com.pipedev.twelvedata.model.websocket.SendMessage;
import com.pipedev.twelvedata.model.websocket.SymbolExtendedMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class TwelveDataWS {

    private final String wsUrl;
    private final String apiKey;

    private final WSConnection wsConnection;
    private final TwelveDataListener listeners;


    public TwelveDataWS(String wsUrl, String apiKey, TwelveDataListener listeners) {
        this.wsUrl = wsUrl;
        this.apiKey = apiKey;
        this.listeners = listeners;
        this.wsConnection = new WSConnection(this.wsUrl, this.apiKey, this::onMessage, this::onSession, this::onError);
    }

    public void connect() {
        wsConnection.connect();
    }

    public void close() {
        wsConnection.close();
    }

    public void restart() {
        wsConnection.restart();
    }

    public void subscribe(List<String> symbols) {
        SendMessage message = new SendMessage();
        message.setAction(MessageActionEnum.SUBSCRIBE);
        message.setParams(Map.of("symbols", String.join(",", symbols)));
        wsConnection.sendMessage(message);
    }

    public void subscribeSymbolExtended(List<SymbolExtendedMessage> symbol) {
        SendMessage message = new SendMessage();
        message.setAction(MessageActionEnum.SUBSCRIBE);
        message.setParams(Map.of("symbols", symbol));
        wsConnection.sendMessage(message);
    }

    private void onMessage(Object message, ServerEventEnum event) {
        log.debug("Message: {} Event: {}", message, event);
        listeners.onMessage(message, event);
        if(event == ServerEventEnum.PRICE) {
            listeners.onQuote((SymbolQuote) message);
        }
        if(event == ServerEventEnum.SUBSCRIBE_STATUS) {
            listeners.onSymbolsSubscription((List<SymbolSubscription>) message);
        }
    }

    private void onSession(boolean connected) {
        if (!connected) {
            log.info("server disconnected");
            return;
        }
        log.info("server connected");
        listeners.onMessage(null, ServerEventEnum.CONNECTION);
        listeners.onConnection();
    }

    public void onError(Throwable error, String message) {
        log.error("ws error {}", message, error);
        if (listeners == null) {
            return;
        }
        listeners.onError(error, message);
    }

}
