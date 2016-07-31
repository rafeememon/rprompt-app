package com.rbase.rprompt;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RPromptServices {

    private RPromptServices() {
        // utility class
    }

    public static RPromptService newDefaultService() {
        return new DefaultRPromptService(new HashMap<>());
    }

    public static RPromptService newExpiringService(long duration, TimeUnit unit) {
        return ExpiringRPromptService.create(duration, unit);
    }

}
