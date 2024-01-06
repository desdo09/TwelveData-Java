package com.pipedev.twelvedata.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ServerEventEnum {
    SUBSCRIBE_STATUS("subscribe-status"), CONNECTION("connection"), HEARTBEAT("heartbeat"), PRICE("price");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ServerEventEnum fromValue(String value) {
        return Arrays.stream(ServerEventEnum.values())
                .filter(op -> op.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }
}
