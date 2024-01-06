package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeekMetric {
    @JsonProperty("low")
    private String low;

    @JsonProperty("high")
    private String high;

    @JsonProperty("low_change")
    private String lowChange;

    @JsonProperty("high_change")
    private String highChange;

    @JsonProperty("low_change_percent")
    private String lowChangePercent;

    @JsonProperty("high_change_percent")
    private String highChangePercent;

    @JsonProperty("range")
    private String range;
}
