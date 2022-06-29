package org.acme;

import java.util.HashMap;
import java.util.Set;

import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.config.*;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.jboss.logging.Logger;

public class ExampleInterceptorFactory implements ConfigSourceInterceptorFactory {

    private static final Logger Log = Logger.getLogger(ExampleInterceptorFactory.class);

        @Override
        public ConfigSourceInterceptor getInterceptor(final ConfigSourceInterceptorContext configSourceInterceptorContext) {
            Log.info("================> ExampleInterceptorFactory");
            return new DuplicateSecretKeysConfigSourceInterceptor(Set.of("db.username", "db.password","db.myprop1", "db.myprop2"));
        }

}
