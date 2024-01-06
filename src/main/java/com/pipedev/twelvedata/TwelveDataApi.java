package com.pipedev.twelvedata;

import com.pipedev.twelvedata.model.data.CurrencyDetails;
import com.pipedev.twelvedata.model.data.Exchange;
import com.pipedev.twelvedata.model.data.MarketState;
import com.pipedev.twelvedata.model.data.StockDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class TwelveDataApi {

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;

    public TwelveDataApi(RestTemplate restTemplate, String url, String apiKey) {
        this.restTemplate = restTemplate;
        this.url = url.trim().endsWith("/") ? url.trim().substring(0, url.length() - 1).trim() : url.trim();
        this.apiKey = apiKey;
    }

    public List<StockDetails> getAllStockDetails() {

        var response = restTemplate.exchange(getUrl("/stocks?show_plan=true"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<StockDetails>>() {
                }
        );
        handleResponse(response);
        return response.getBody();
    }

    public List<StockDetails> getStockDetails(String symbol, String exchange, String country, String type) {
        var response = restTemplate.exchange(getUrl("/stocks?symbol={symbol}&exchange={exchange}&country={country}&type={type}&show_plan=true"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<StockDetails>>() {
                },
                Objects.requireNonNullElse(symbol, ""),
                Objects.requireNonNullElse(exchange, ""),
                Objects.requireNonNullElse(country, ""),
                Objects.requireNonNullElse(type, "")
        );
        handleResponse(response);
        return response.getBody();
    }

    public List<CurrencyDetails> getAllForexDetails() {
        var response = restTemplate.exchange(getUrl("/forex_pairs"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<CurrencyDetails>>() {
                }
        );
        handleResponse(response);
        return response.getBody();
    }


    /***
     * Get forex details by symbol, currency base and currency quote
     * @param currencyBase: base currency name according to ISO 4217 standard
     * @param currencyQuote: quote currency name according to ISO 4217 standard
     */
    public List<CurrencyDetails> getForexDetails(String symbol, String currencyBase, String currencyQuote) {
        var response = restTemplate.exchange(getUrl("/forex_pairs?symbol={symbol}&currency_base={currency_base}&currency_quote={currency_quote}"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<CurrencyDetails>>() {
                },
                Objects.requireNonNullElse(symbol, "")
        );
        handleResponse(response);
        return response.getBody();
    }

    public List<CurrencyDetails> getAllCryptoDetails() {
        var response = restTemplate.exchange(getUrl("/cryptocurrencies"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<CurrencyDetails>>() {
                }
        );
        handleResponse(response);
        return response.getBody();
    }

    /***
     * Get crypto details by symbol, exchange, currency base and currency quote
     * @param exchange: Filter by exchange name
     * @param currencyBase: Filter by currency base
     * @param currencyQuote: Filter by currency quote
     */
    public List<CurrencyDetails> getCryptoDetails(String symbol, String exchange, String currencyBase, String currencyQuote) {
        var response = restTemplate.exchange(getUrl("/cryptocurrencies?symbol={symbol}}&exchange={exchange}&currency_base={currency_base}&currency_quote={currency_quote}"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<CurrencyDetails>>() {
                },
                Objects.requireNonNullElse(symbol, ""),
                Objects.requireNonNullElse(exchange, ""),
                Objects.requireNonNullElse(currencyBase, ""),
                Objects.requireNonNullElse(currencyQuote, "")
        );
        handleResponse(response);
        return response.getBody();
    }

    public List<StockDetails> getAllETFDetails() {

        var response = restTemplate.exchange(getUrl("/etf?show_plan=true"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<StockDetails>>() {
                }
        );
        handleResponse(response);
        return response.getBody();
    }

    public List<StockDetails> getETFDetails(String symbol, String exchange, String country, String type) {
        var response = restTemplate.exchange(getUrl("/etf?symbol={symbol}&exchange={exchange}&country={country}&type={type}&show_plan=true"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<StockDetails>>() {
                },
                Objects.requireNonNullElse(symbol, ""),
                Objects.requireNonNullElse(exchange, ""),
                Objects.requireNonNullElse(country, ""),
                Objects.requireNonNullElse(type, "")
        );
        handleResponse(response);
        return response.getBody();
    }

    //Same for indices
    public List<StockDetails> getAllIndexDetails() {

        var response = restTemplate.exchange(getUrl("/indices?show_plan=true"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<StockDetails>>() {
                }
        );
        handleResponse(response);
        return response.getBody();
    }

    public List<StockDetails> getIndexDetails(String symbol, String exchange, String country) {
        var response = restTemplate.exchange(getUrl("/indices?symbol={symbol}&exchange={exchange}&country={country}&show_plan=true"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<StockDetails>>() {
                },
                Objects.requireNonNullElse(symbol, ""),
                Objects.requireNonNullElse(exchange, ""),
                Objects.requireNonNullElse(country, "")
        );
        handleResponse(response);
        return response.getBody();
    }


    public List<Exchange> getExchanges() {
        var response = restTemplate.exchange(getUrl("/exchanges"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<Exchange>>() {
                }
        );
        handleResponse(response);
        return response.getBody();
    }

    //Get crypto exchanges
    public List<Exchange> getCryptoExchanges() {
        var response = restTemplate.exchange(getUrl("/cryptocurrency_exchanges"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<Exchange>>() {
                }
        );
        handleResponse(response);
        return response.getBody();
    }

    //Market State
    public List<MarketState> getMarketState() {
        var response = restTemplate.exchange(getUrl("/market_state?apikey={apikey}"),
                HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<List<MarketState>>() {
                },
                apiKey
        );
        handleResponse(response);
        return response.getBody();
    }


    private String getUrl(String path) {
        return String.format("%s/%s", url, path);
    }


    private void handleResponse(ResponseEntity<?> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            return;
        }
        if(response.getBody() == null)
            throw new RuntimeException(String.format("Error while calling 12Data API. Status code: %s", response.getStatusCode()));
        else
            throw new RuntimeException(String.format("Error while calling 12Data API. Status code: %s, message: %s",
                    response.getStatusCode(), response.getBody()));
    }


    private HttpEntity<Object> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
