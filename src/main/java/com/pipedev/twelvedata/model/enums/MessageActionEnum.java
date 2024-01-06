package com.pipedev.twelvedata.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageActionEnum {
    SUBSCRIBE("subscribe"),
    HEARTBEAT("heartbeat"),
    UNSUBSCRIBE("unsubscribe");


    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
