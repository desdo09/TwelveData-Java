package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangeRate {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("rate")
    private double rate;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("timestamp")
    private long timestamp;

}
