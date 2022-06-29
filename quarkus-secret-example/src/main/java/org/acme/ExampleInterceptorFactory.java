package org.acme;

import io.smallrye.config.ConfigSourceInterceptor;
import io.smallrye.config.ConfigSourceInterceptorContext;
import io.smallrye.config.ConfigSourceInterceptorFactory;
import io.smallrye.config.SecretKeysConfigSourceInterceptor;

import java.util.Set;

public class ExampleInterceptorFactory implements ConfigSourceInterceptorFactory {
    @Override
    public ConfigSourceInterceptor getInterceptor(final ConfigSourceInterceptorContext configSourceInterceptorContext) {
        return new SecretKeysConfigSourceInterceptor(Set.of("db.username", "db.password"));
    }
}
