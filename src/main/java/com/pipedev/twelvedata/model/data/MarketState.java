package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarketState {

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("country")
    private String country;

    @JsonProperty("is_market_open")
    private boolean isMarketOpen;

    @JsonProperty("time_after_open")
    private String timeAfterOpen;

    @JsonProperty("time_to_open")
    private String timeToOpen;

    @JsonProperty("time_to_close")
    private String timeToClose;

}
