package org.acme;

import io.smallrye.config.*;

import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import javax.annotation.Priority;
import java.util.Set;

@Priority(Priorities.LIBRARY + 100)
public class ExampleInterceptor implements ConfigSourceInterceptor {

    private static final Logger Log = Logger.getLogger(ExampleInterceptor.class);
    private static final String PREFIX = "db.";

    @Override
    public ConfigValue getValue(final ConfigSourceInterceptorContext context, final String name) {
        final ConfigValue configValue = context.proceed(name);
        Log.info("Inttercepting property ["+name+"]");
        if (configValue != null && name.startsWith(PREFIX)) {
            Log.info("SHOULD NEVER BE SEEN LIKE THIS REMOVE TRAITOR ["+configValue+"]");
            return configValue.withValue("intercepted " + configValue.getValue());
        }

        return configValue;


//        if (isSecret(name)) Log.info("name ["+name+"], SecretKeys.isLocked() --> ["+SecretKeys.isLocked()+"], isSecret("+name+") --> ["+isSecret(name)+"]");
//        if (SecretKeys.isLocked() && isSecret(name)) {
//            Log.info("----------------------------------------------------------");
//            Log.info("Locked ["+name+"]");
////            Log.info("context names ["+context.iterateNames()+"]");
////            Log.info("context values ["+context.iterateValues()+"]");
//////            String propValue = SecretKeys.doUnlocked(() -> {
//////                Log.info("----------------SecretKeys.isLocked() 3 --> "+SecretKeys.isLocked());
//////                return (String) ConfigProvider.getConfig().getValue(name, String.class);
//////            });
//////            Log.info("context value ["+propValue+"]");
//            Log.info("----------------------------------------------------------");
//
//            throw ConfigMessages.msg.notAllowed(name);
//        }
//
//        Log.info("----------------------------------------------------------");
//        Log.info("UNLocked ["+name+"]");
//        Log.info("context names ["+context.iterateNames()+"]");
//        Log.info("context values ["+context.iterateValues()+"]");
//        //String propValue = SecretKeys.doUnlocked(() -> {
//        //    Log.info("----------------SecretKeys.isLocked() 3 --> "+SecretKeys.isLocked());
//        //    return (String) ConfigProvider.getConfig().getValue(name, String.class);
//        //});
//        Log.info("context value ["+context.proceed(name).getValue()+"]");
//        Log.info("----------------------------------------------------------");
//        return context.proceed(name);
    }

    private boolean isSecret(final String name) {
        return name.startsWith(PREFIX);
    }

}
