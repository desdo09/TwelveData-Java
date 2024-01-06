package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Exchange {

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("country")
    private String country;

    @JsonProperty("timezone")
    private String timezone;

}
