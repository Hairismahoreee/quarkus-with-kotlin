package com.learn.core.security.jwt.enums;

public enum JwtStatus {
    ACTIVE, REVOKE, ERROR, EXPIRED;

    public String getCodeName() {
        return toString().toLowerCase();
    }
}
