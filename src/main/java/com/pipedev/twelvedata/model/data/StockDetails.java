package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockDetails {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("exchange")
    private String exchange;

    @JsonProperty("mic_code")
    private String micCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("type")
    private String type;

    @JsonProperty("access")
    private PlanAccess access;
}
