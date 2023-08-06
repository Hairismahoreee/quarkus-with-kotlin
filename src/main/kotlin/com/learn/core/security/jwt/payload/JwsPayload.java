package com.learn.core.security.jwt.payload;

import com.learn.core.security.jwt.enums.JwtType;
import com.learn.core.util.CheckUtil;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class JwsPayload {
    private final HeaderPayload header;
    private final BodyPayload body;
    private final String token;
    private final Date expiredDate;
    private final JwtType type;

    public JwsPayload(HeaderPayload header, BodyPayload body, String token, Date exp, JwtType type) {
        this.header = Objects.requireNonNull(header, "header is null");
        this.body = Objects.requireNonNull(body, "body is null");
        this.expiredDate = Objects.requireNonNull(exp, "exp is null");
        this.type = Objects.requireNonNull(type, "type is null");
        if(CheckUtil.isStringBlank(token)) {
            throw new NullPointerException("token is blank");
        }else {
            this.token = token;
        }

    }
}
