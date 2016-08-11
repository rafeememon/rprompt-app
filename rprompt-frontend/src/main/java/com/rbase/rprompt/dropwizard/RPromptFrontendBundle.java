package com.rbase.rprompt.dropwizard;

import io.dropwizard.Bundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RPromptFrontendBundle implements Bundle {

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(Environment environment) {
        // no-op
    }

}
