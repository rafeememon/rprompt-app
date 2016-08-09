package com.rbase.rprompt;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RPromptBackends {

    private RPromptBackends() {
        // utility class
    }

    public static RPromptBackend newDefaultBackend() {
        return new DefaultRPromptBackend(new HashMap<>());
    }

    public static RPromptBackend newExpiringBackend(long duration, TimeUnit unit) {
        return ExpiringRPromptBackend.create(duration, unit);
    }

}
