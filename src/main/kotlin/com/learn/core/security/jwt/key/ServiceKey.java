package com.learn.core.security.jwt.key;

import io.jsonwebtoken.security.Keys;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.ConfigProvider;

import java.security.Key;
import java.util.Base64;

@Singleton
public class ServiceKey {

    private static final String KEY_ENV_NAME = "dimata.jwt.service-key.secret";
    private static final String VERSION_ENV_NAME = "dimata.jwt.service-key.version";
    
    private Key privateKey;
    private String version;

    public ServiceKey() {
        var encodedKey = ConfigProvider.getConfig().getValue(KEY_ENV_NAME, String.class);
        var decoder = Base64.getDecoder();
        var result = decoder.decode(encodedKey);
        this.privateKey = Keys.hmacShaKeyFor(result);
        this.version = ConfigProvider.getConfig().getValue(VERSION_ENV_NAME, String.class);
        Log.info("[Service Key] Service Key success initialized...");
    }

    public Key getKey() {
        return privateKey;
    }

    public String getVersion() {
        return version;
    }
}
