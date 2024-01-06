package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SymbolQuote {
    private String event;
    private String symbol;
    private String currency;
    private String exchange;
    private String type;
    private Long timestamp;
    private Double price;
    private Double bid, ask;
    @JsonProperty("data_volume")
    private Long dayVolume;
}
