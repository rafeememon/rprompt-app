package com.rbase.rprompt.dropwizard;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class RPromptApplicationConfiguration extends Configuration {

    @Valid
    @NotNull
    private RPromptConfiguration rprompt = new RPromptConfiguration();

    @JsonProperty
    public RPromptConfiguration getRPrompt() {
        return rprompt;
    }

    @JsonProperty
    public void setRPrompt(RPromptConfiguration rprompt) {
        this.rprompt = rprompt;
    }

}
