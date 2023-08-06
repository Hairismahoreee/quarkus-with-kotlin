package com.learn.core.security.jwt.payload;

import com.learn.core.util.CheckUtil;
import com.learn.core.exception.ExceptionCode;
import com.learn.core.exception.FormatException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderPayload {
    /**
     * Algoritma didalam JWT disingkat <b>>alg</b> merupakan algorimat apa yang digunakan untuk mengsignatur JWT ini.
     */
    private final String algorithm;
    /**
     * Type didalam JWT disingkat <b>typ</b>, tipe dari JWT ini defualtnya JWT.
     */
    private final String type;
    /**
     * Content Type didalam JWT disingkat <b>cty</b>, informasi tambahan biasanya tidak diiisi.
     */
    private final String contentType;
    /**
     * KeyId didalam JWT disingkat <b>kid</b> merupakan informasi id dari public key untuk verifikasi JWT ini.
     */
    private final String keyId;


    public static HeaderPayload fromKid(String keyId) {
        return ofKidAlgo(keyId, null);
    }

    public static HeaderPayload ofKidAlgo(String keyId, String algorithm) {
        Objects.requireNonNull(keyId, "KeyId can't be null");
        var type = "JWT";
        return new HeaderPayload(algorithm, type, null, keyId);
    }
    
    /**
     * Setidaknya KID ada.
     */
    public void validateClaim() {
        if(CheckUtil.isStringBlank(getKeyId())) {
            throw new FormatException(ExceptionCode.JWT_PAYLOAD_NO_VALID, "Header payload");
        }
    }
}
