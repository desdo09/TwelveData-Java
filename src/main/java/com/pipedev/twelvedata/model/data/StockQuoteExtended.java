package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockQuoteExtended {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("exchange")
    private String exchange;

    @JsonProperty("mic_code")
    private String micCode;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("open")
    private String open;

    @JsonProperty("high")
    private String high;

    @JsonProperty("low")
    private String low;

    @JsonProperty("close")
    private String close;

    @JsonProperty("volume")
    private String volume;

    @JsonProperty("previous_close")
    private String previousClose;

    @JsonProperty("change")
    private String change;

    @JsonProperty("percent_change")
    private String percentChange;

    @JsonProperty("average_volume")
    private String averageVolume;

    @JsonProperty("rolling_1d_change")
    private String rolling1dChange;

    @JsonProperty("rolling_7d_change")
    private String rolling7dChange;

    @JsonProperty("rolling_period_change")
    private String rollingPeriodChange;

    @JsonProperty("is_market_open")
    private boolean isMarketOpen;

    @JsonProperty("fifty_two_week")
    private WeekMetric fiftyTwoWeek;

    @JsonProperty("extended_change")
    private String extendedChange;

    @JsonProperty("extended_percent_change")
    private String extendedPercentChange;

    @JsonProperty("extended_price")
    private String extendedPrice;

    @JsonProperty("extended_timestamp")
    private long extendedTimestamp;
}
