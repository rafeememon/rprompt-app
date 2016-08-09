package com.rbase.rprompt.backend;

import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RPromptBackends {

    private RPromptBackends() {
        // utility class
    }

    public static RPromptBackend newDefaultBackend() {
        return new DefaultRPromptBackend(new HashMap<>());
    }

    public static RPromptBackend newExpiringBackend(ScheduledExecutorService executor, long duration, TimeUnit unit) {
        return ExpiringRPromptBackend.create(executor, duration, unit);
    }

}
