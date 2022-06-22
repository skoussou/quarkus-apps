package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.config.SecretKeys;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class DuplicateSecretKeysConfigSourceInterceptorTest {

    private static final Logger Log = Logger.getLogger(DuplicateSecretKeysConfigSourceInterceptorTest.class);

    @Test
    public void testSecretKeysSecurityExceptions()  {
        SecurityException thrown = Assertions.assertThrows(SecurityException.class, () -> {ConfigProvider.getConfig().getValue("db.myprop1", String.class);});
        Assertions.assertEquals("SRCFG00024: Not allowed to access secret key db.myprop1", thrown.getMessage());

    }


    @Test
    void testSecretKeysInterceptorgetValue() {
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
        assertEquals("prop1", myprop1SecretValue);

        String myprop2SecretValue = SecretKeys.doUnlocked(() -> {
            return (String) ConfigProvider.getConfig().getValue("db.myprop2", String.class);
            //ConfigProvider.getConfig().getValue("db.myprop2", String.class);
            //config.getValue("db.myprop2", String.class);
            //return smallRyeConfig.getValue("db.myprop2", String.class);

        });
      //final String myPassword = ConfigProvider.getConfig().getValue("db.myprop2", String.class);
        //assertEquals("intercepted testpassword", myprop2SecretValue);
        assertEquals("prop2", myprop2SecretValue);
    }
}