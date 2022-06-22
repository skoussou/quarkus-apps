package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.logging.Log;
import io.quarkus.runtime.configuration.QuarkusConfigFactory;
import io.smallrye.config.SecretKeys;
import io.smallrye.config.SmallRyeConfig;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

class ExampleInterceptorTest {

    private static final Logger Log = Logger.getLogger(ExampleInterceptorTest.class);

    //core/runtime/src/main/java/io/quarkus/runtime/configuration/QuarkusConfigFactory.java
    //private static volatile SmallRyeConfig config;
//    @Inject
//    ExampleSmallRyeConfigFactory factory;

    @Test
    void getValue() {
        //org.eclipse.microprofile.config.Config config = ConfigProvider.getConfig();
       // SmallRyeConfig smallRyeConfig = factory.getConfigFor(ConfigProviderResolver.instance(), this));
        Log.info("what");
        //SmallRyeConfig smallRyeConfig = factory.getConfig();
        Log.info("----------------SecretKeys.isLocked() 1 --> "+SecretKeys.isLocked());
//        String myprop1SecretValue = ConfigProvider.getConfig().getValue("db.myprop1", String.class);
        String myprop1SecretValue = SecretKeys.doUnlocked(() -> {

            if (ConfigProvider.getConfig() == null) Log.info("ConfigProvider is NULL");
            if (ConfigProvider.getConfig() != null) Log.info("ConfigProvider is NOT NULL");
            return (String) ConfigProvider.getConfig().getValue("db.myprop1", String.class);
//            //config.getValue("db.myprop1", String.class);
//            //return (String) smallRyeConfig.getValue("db.myprop1", String.class);
        });
        //final String myUsername = ConfigProvider.getConfig().getValue("db.myprop1", String.class);
        //assertEquals("intercepted testusername", myprop1SecretValue);
        assertEquals("testusername2", myprop1SecretValue);

        String myprop2SecretValue = SecretKeys.doUnlocked(() -> {
            return (String) ConfigProvider.getConfig().getValue("db.myprop2", String.class);
            //ConfigProvider.getConfig().getValue("db.myprop2", String.class);
            //config.getValue("db.myprop2", String.class);
            //return smallRyeConfig.getValue("db.myprop2", String.class);

        });
      //final String myPassword = ConfigProvider.getConfig().getValue("db.myprop2", String.class);
        //assertEquals("intercepted testpassword", myprop2SecretValue);
        assertEquals("testpassword2", myprop2SecretValue);
    }
}