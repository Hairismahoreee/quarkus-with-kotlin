package com.learn.core.security.jwt.payload;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.security.Key;

@EqualsAndHashCode
@ToString
@Getter
public class JwsBuilder {
    private final Key privateKey;

    private HeaderPayload header;
    private BodyPayload body;

    public static JwsBuilder create(Key privateKey) {
        return new JwsBuilder(privateKey);
    }

    private JwsBuilder(Key privateKey) {
        this.privateKey = privateKey;
    }

    public JwsBuilder setHeader(HeaderPayload payload) {
        payload.validateClaim();
        this.header = payload;
        return this;
    }

    public JwsBuilder setBody(BodyPayload payload) {
        this.body = payload;
        return this;
    }

    public JwsPayload buildPayload(String token) {
        var expiredDate = body.getExpiredClaim();
        var tokenType = body.getTypeClaim();
        return new JwsPayload(header, body, token, expiredDate, tokenType);
    }

}
