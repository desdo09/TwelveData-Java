package com.pipedev.twelvedata.model.websocket;

import com.pipedev.twelvedata.model.enums.MessageActionEnum;
import lombok.Data;

import java.util.Map;

@Data
public class SendMessage {

    private MessageActionEnum action;
    private Map<String, Object> params;
}
