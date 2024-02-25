package com.pipedev.twelvedata.connection;

import com.pipedev.twelvedata.model.websocket.SessionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class WSHandler implements WebSocketHandler {

    private Boolean isConnected = false;
    private final Consumer<String> messageConsumer;
    private final Consumer<SessionStatus> sessionConsumer;

    public WSHandler(Consumer<String> messageConsumer, Consumer<SessionStatus> sessionConsumer) {
        this.messageConsumer = messageConsumer;
        this.sessionConsumer = sessionConsumer;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        isConnected = true;
        sessionConsumer.accept(new SessionStatus(session, true));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        message.getPayload();
        messageConsumer.accept(message.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        isConnected = false;
        sessionConsumer.accept(new SessionStatus(session, false, exception, exception.getMessage()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        isConnected = false;
        sessionConsumer.accept(new SessionStatus(session, false, closeStatus, closeStatus.getReason()));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
