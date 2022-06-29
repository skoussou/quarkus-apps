package org.acme;

import io.smallrye.config.*;
import org.jboss.logging.Logger;

import java.util.Set;
import javax.annotation.Priority;

@Priority(Priorities.LIBRARY + 100)
public class DuplicateSecretKeysConfigSourceInterceptor implements ConfigSourceInterceptor {
    private static final long serialVersionUID = 7291982039729980590L;

   // private static final Logger Log = Logger.getLogger(DuplicateSecretKeysConfigSourceInterceptor.class);

    private static final String PREFIX = "db.";

    private final Set<String> secrets;


    public DuplicateSecretKeysConfigSourceInterceptor() {
        this.secrets = null;
    }


    public DuplicateSecretKeysConfigSourceInterceptor(Set<String> secrets) {
        this.secrets = secrets;
    }

    @Override
    public ConfigValue getValue(final ConfigSourceInterceptorContext context, final String name) {
        final ConfigValue configValue = context.proceed(name);
        // if (isSecret(name)) Log.info("name ["+name+"], SecretKeys.isLocked() --> ["+SecretKeys.isLocked()+"], isSecret("+name+") --> ["+isSecret(name)+"]");
        return configValue;
    }

    private boolean isSecret(final String name)  {
        //return name.startsWith(PREFIX);
        return secrets.contains(name);
    }
}

//        if (isSecret(name)) Log.info("   1  [] ");
//
//                //if (isSecret(name)) System.out.println("DuplicateSecretKeysConfigSourceInterceptor.getValue ");
//                if (isSecret(name)) Log.info("name ["+name+"], SecretKeys.isLocked() --> ["+SecretKeys.isLocked()+"], isSecret("+name+") --> ["+isSecret(name)+"]");
//                if (SecretKeys.isLocked() && isSecret(name)) {
//                throw ConfigMessages.msg.notAllowed(name);
//                }
//                if (isSecret(name)) Log.info("name ["+name+"] value now retrieved via context");
//                if (isSecret(name)) Log.info("------");
//                if (isSecret(name) && context == null) Log.info("context is NULL");
//                if (isSecret(name) && context.proceed(name) == null) {
//                Log.info("context.proceed("+name+") is NULL");
////            while (context.iterateValues().hasNext()) {
////                Log.info("context is [" + context.iterateValues().next() + "]");
////            }
//                }
//                if (isSecret(name)) Log.info("name ["+name+"], value ["+context.proceed(name).getValue()+"]");
//                if (isSecret(name)) Log.info("------");
//
////        if (isSecret(name)) Log.info("name ["+name+"], value ["+context.proceed(name).getValue()+"], SecretKeys.isLocked() --> ["+SecretKeys.isLocked()+"], isSecret("+name+") --> ["+isSecret(name)+"]");