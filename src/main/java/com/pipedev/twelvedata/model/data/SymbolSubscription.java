package com.pipedev.twelvedata.model.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SymbolSubscription {
    private String symbol;
    private String exchange;
    private String country;
    private String type;
}
