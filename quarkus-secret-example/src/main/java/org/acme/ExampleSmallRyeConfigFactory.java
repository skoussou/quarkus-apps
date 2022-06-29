package org.acme;

import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigFactory;
import io.smallrye.config.SmallRyeConfigProviderResolver;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.jboss.logging.Logger;

public class ExampleSmallRyeConfigFactory extends SmallRyeConfigFactory {

    private static final Logger Log = Logger.getLogger(ExampleInterceptorFactory.class);

    private static volatile SmallRyeConfig config;

    @Override
    public SmallRyeConfig getConfigFor(
            final SmallRyeConfigProviderResolver configProviderResolver, final ClassLoader classLoader) {
        Log.info("================> ExampleSmallRyeConfigFactory");
        return configProviderResolver.getBuilder().forClassLoader(classLoader)
                .addDefaultSources()
                .addDiscoveredSources()
                .addDiscoveredConverters()
                .addDiscoveredInterceptors()
                .withProfile(ProfileManager.getActiveProfile())
                .withSecretKeys("db.myprop1","db.myprop2").addDefaultInterceptors()
                .build();
    }

    public SmallRyeConfig getConfig(){
        return config;
    };

    /**
     * Construct a new instance. Called by service loader.
     */
    public ExampleSmallRyeConfigFactory() {
        // todo: replace with {@code provider()} post-Java 11
    }

    public static void setConfig(SmallRyeConfig config) {
        if (ExampleSmallRyeConfigFactory.config != null) {
            ConfigProviderResolver.instance().releaseConfig(ExampleSmallRyeConfigFactory.config);
        }
    }

}
