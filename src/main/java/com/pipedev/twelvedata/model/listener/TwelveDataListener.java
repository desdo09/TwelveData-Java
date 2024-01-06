package com.pipedev.twelvedata.model.listener;

import com.pipedev.twelvedata.model.data.SymbolQuote;
import com.pipedev.twelvedata.model.data.SymbolSubscription;
import com.pipedev.twelvedata.model.enums.ServerEventEnum;

import java.util.List;


public interface TwelveDataListener {
    default void onConnection(){};
    default void onMessage(Object message, ServerEventEnum event){};
    default void onError(Throwable error, String message){};
    default void onQuote(SymbolQuote quote){};
    default void onSymbolsSubscription(List<SymbolSubscription> symbolSubscription){};
}
