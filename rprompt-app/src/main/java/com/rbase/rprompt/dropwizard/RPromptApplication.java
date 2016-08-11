package com.rbase.rprompt.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RPromptApplication extends Application<RPromptApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            new RPromptApplication().run(new String[] { "server", "src/test/resources/rprompt.yml" });
        } else {
            new RPromptApplication().run(args);
        }
    }

    @Override
    public String getName() {
        return "RPrompt";
    }

    @Override
    public void initialize(Bootstrap<RPromptApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(new RPromptBackendBundle());
        bootstrap.addBundle(new RPromptFrontendBundle());
    }

    @Override
    public void run(RPromptApplicationConfiguration configuration, Environment environment) {
        // no-op
    }

}
