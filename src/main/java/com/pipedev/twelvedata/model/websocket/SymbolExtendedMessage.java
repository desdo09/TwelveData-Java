package com.pipedev.twelvedata.model.websocket;

import lombok.Data;

@Data
public class SymbolExtendedMessage {

    private String symbol;
    private String exchange;
}
