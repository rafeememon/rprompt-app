package com.rbase.rprompt.dropwizard;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.rbase.rprompt.backend.RPromptBackend;
import com.rbase.rprompt.backend.RPromptBackends;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RPromptBackendBundle implements ConfiguredBundle<RPromptApplicationConfiguration> {

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // no-op
    }

    @Override
    public void run(RPromptApplicationConfiguration configuration, Environment environment) throws Exception {
        ScheduledExecutorService executor = environment.lifecycle()
                .scheduledExecutorService("RPromptConnectionCache", true).build();
        RPromptBackend backend = RPromptBackends.newExpiringBackend(
                executor, configuration.getRPrompt().getConnectionTimeoutMinutes(), TimeUnit.MINUTES);
        environment.jersey().register(new RPromptBackendResource(backend));
    }

}
