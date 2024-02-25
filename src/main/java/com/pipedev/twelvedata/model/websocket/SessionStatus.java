package com.pipedev.twelvedata.model.websocket;

import lombok.Data;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Data
public class SessionStatus {
    private WebSocketSession session;
    private boolean isConnected;
    private Throwable exception;
    private CloseStatus closeStatus;
    private String reason;

    public SessionStatus(WebSocketSession session, boolean isConnected) {
        this.session = session;
        this.isConnected = isConnected;
    }

    public SessionStatus(WebSocketSession session, boolean isConnected, Throwable exception, String reason) {
        this.session = session;
        this.isConnected = isConnected;
        this.exception = exception;
        this.reason = reason;
    }

    public SessionStatus(WebSocketSession session, boolean isConnected, CloseStatus closeStatus, String reason) {
        this.session = session;
        this.isConnected = isConnected;
        this.closeStatus = closeStatus;
        this.reason = reason;
    }
}
