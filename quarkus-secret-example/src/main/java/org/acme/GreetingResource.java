package org.acme;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.config.SecretKeys;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

    private static final Logger Log = Logger.getLogger(GreetingResource.class);

//    @ConfigProperty(name = "username")
//    String username;

//    @ConfigProperty(name = "password")
//    String password;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/security")
    public Map securty() {
        HashMap<String, String> map = new HashMap<>();

        String dbUsernameUnlockedSecretValue = SecretKeys.doUnlocked(() -> {
            return (String) ConfigProvider.getConfig().getValue("db.username", String.class);
        });
        String dbPasswordUnlockedSecretValue = SecretKeys.doUnlocked(() -> {
            return (String) ConfigProvider.getConfig().getValue("db.password", String.class);
        });
        Log.info("db.username : " + dbUsernameUnlockedSecretValue);
        Log.info("db.password : " + dbPasswordUnlockedSecretValue);
        map.put("db.username", dbUsernameUnlockedSecretValue);
        map.put("db.password", dbPasswordUnlockedSecretValue);

//        Log.info("db.username : " + ConfigProvider.getConfig().getValue("db.username", String.class));
//        Log.info("db.password : " + ConfigProvider.getConfig().getValue("db.password", String.class));
//        map.put("db.username", ConfigProvider.getConfig().getValue("db.username", String.class));
//        map.put("db.password", ConfigProvider.getConfig().getValue("db.password", String.class));

        return map;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }
}