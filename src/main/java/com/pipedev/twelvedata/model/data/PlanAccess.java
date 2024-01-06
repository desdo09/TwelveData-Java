package com.pipedev.twelvedata.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlanAccess {
    @JsonProperty("global")
    private String global;

    @JsonProperty("plan")
    private String plan;
}
