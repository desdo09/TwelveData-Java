package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CurrencyDetails {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("currency_group")
    private String currencyGroup;

    @JsonProperty("available_exchanges")
    private List<String> availableExchanges;

    @JsonProperty("currency_base")
    private String currencyBase;

    @JsonProperty("currency_quote")
    private String currencyQuote;
}
