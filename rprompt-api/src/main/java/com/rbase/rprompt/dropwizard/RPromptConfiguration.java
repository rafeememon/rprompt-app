package com.rbase.rprompt.dropwizard;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RPromptConfiguration {

    @Valid
    @Min(1)
    private long connectionTimeoutMinutes;

    @JsonProperty
    public long getConnectionTimeoutMinutes() {
        return connectionTimeoutMinutes;
    }

    @JsonProperty
    public void setConnectionTimeoutMinutes(long connectionTimeoutMinutes) {
        this.connectionTimeoutMinutes = connectionTimeoutMinutes;
    }

}
