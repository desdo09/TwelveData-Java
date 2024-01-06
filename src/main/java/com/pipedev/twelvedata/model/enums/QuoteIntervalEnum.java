package com.pipedev.twelvedata.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum QuoteIntervalEnum {

    MIN_1("1min"), MIN_5("5min"), MIN_15("15min"), MIN_30("30min"), MIN_45("45min"),
    HOUR_1("1h"), HOUR_2("2h"), HOUR_4("4h"),
    DAY_1("1day"), WEEK_1("1week"), MONTH_1("1month");

    private final String value;

    QuoteIntervalEnum(String interval) {
        this.value = interval;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }
}
