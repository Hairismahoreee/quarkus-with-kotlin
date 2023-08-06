package com.learn.core.security.jwt.handler;

import com.learn.core.security.jwt.payload.BodyPayload;
import com.learn.core.security.jwt.payload.HeaderPayload;
import com.learn.core.security.jwt.payload.JwsBuilder;
import com.learn.core.security.jwt.payload.JwsPayload;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.Key;

@ApplicationScoped
public class JwtCreationHandler {
    
    public JwsPayload createAccessKey(HeaderPayload header, BodyPayload body, Key privateKey) {
        var jwsBuilder = JwsBuilder.create(privateKey)
            .setHeader(header)
            .setBody(body);

        var jwtBuilder = Jwts.builder()
            .setHeaderParam("kid", jwsBuilder.getHeader().getKeyId())
            .setClaims(jwsBuilder.getBody().buildClaims())
            .signWith(jwsBuilder.getPrivateKey());
        
        return jwsBuilder.buildPayload(jwtBuilder.compact());
    }

}
