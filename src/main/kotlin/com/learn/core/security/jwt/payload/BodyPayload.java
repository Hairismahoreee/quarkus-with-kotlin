package com.learn.core.security.jwt.payload;

import com.learn.core.security.jwt.enums.JwtType;
import com.learn.core.util.FormatUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BodyPayload {
    public static final String APP_ID = "appId";
    public static final String ROLES = "roles";
    public static final String ISSUER = "iss";
    public static final String SUBJECT = "sub";
    public static final String AUDIENCE = "aud";
    public static final String JWT_ID = "jti";
    public static final String ISSUED_AT = "iat";
    public static final String TYPE = "type";
    public static final String EXPIRED = "exp";
    public static final String NOT_BERFORE = "nbf";
    public static final String USER_ID = "userId";
    public static final String FIREBASE_UID = "firebaseUid";
    public static final String TENANT_ID = "tenantId";

    /**
     * issuer didalam jwt disingkat <b>iss</b>, merupakan identitas unik siapa / darimana JWT ini dibuat.
     */
    private final String issuerClaim;
    /**
     * Subject didalam jwt disingkat <b>sub</b>, merupakan identitas unik pemiliki JWT ini.
     */
    private final String subjectClaim;
    /**
     * Audiences didalam jwt disingkat <b>aud</b>, merupakan kumpulan identitas unik siapa saja penerima JWT ini.
     */
    private final Set<String> audienceClaims;
    /**
     * Roles merupakan kumpulan role yang dimiliki oleh JWT ini.
     */
    private final Set<String> roleClaims;
    /**
     * Jwt Id didalam jwt disingkat <b>jti</b>, merupakan id dari JWT ini dalam bentuk UUID.
     */
    private final String jwtIdClaim;
    /**
     * Issued At didalam jwt disingkat <b>iat</b>, merupakan tanggal kapan JWT ini dibuat.
     */
    private final Date issuedAtClaim;
    /**
     * Jwt type merupakan tipe jwt ini.
     */
    private final JwtType typeClaim;
    /**
     * Custom claims merupakan claims tambahan pada body ini.
     */
    private final Map<String, Object> customClaims;
    /**
     * Expired didalam JWT disingkat <b>exp</b>, merupakan tanggal kapan JWT ini kadarluarsa.
     */
    @With(AccessLevel.PRIVATE)
    private final Date expiredClaim;
    /**
     * Not Berfore didalam JWT disingkat <b>nbf</b>, merupakan mulai tanggal kapan JWT ini boleh diakses.
     */
    @With(AccessLevel.PRIVATE)
    private final Date notBeforeClaim;

    public BodyPayload(String issuerClaim, String subjectClaim, String jwtIdClaim, JwtType typeClaim) {
        this.issuerClaim = issuerClaim;
        this.subjectClaim = subjectClaim;
        this.jwtIdClaim = jwtIdClaim;
        this.typeClaim = typeClaim;
        audienceClaims = new HashSet<>();
        roleClaims = new HashSet<>();
        issuedAtClaim = new Date();
        expiredClaim = new Date();
        notBeforeClaim = new Date();
        customClaims = new HashMap<>();
    }

    /**
     * Tambah Audience baru. Setiap audience unik jadi tidak ada audience yang sama. <br>
     *
     * @param audience Audience baru.
     */
    public BodyPayload addAudience(String audience) {
        this.audienceClaims.add(audience);
        return this;
    }

    /**
     * Menambahkan semua aduiance baru. Setiap audience unik jadi tidak ada audience yang sama.
     *
     * @param audiences Kumpulan audience baru.
     */
    public BodyPayload addAudiences(Collection<String> audiences) {
        this.audienceClaims.addAll(audiences);
        return this;
    }

    /**
     * Menambahkan role baru, setiap role unik jadi tidak ada yang sama.
     *
     * @param role Role yang ingin ditambahkan.
     */
    public BodyPayload addRole(String role) {
        this.roleClaims.add(role);
        return this;
    }

    /**
     * Menambahkan kumpulan roles baru, setiap role unik jadi tidak ada yang sama.
     *
     * @param roles Kumpulan roles yang ingin ditambahkan.
     */
    public BodyPayload addRoles(Collection<String> roles) {
        this.roleClaims.addAll(roles);
        return this;
    }

    /**
     * Mengset expired JWT time dengan menambahkan waktu dari issued at.
     *
     * @param duration Panjang durasi.
     * @param unit     Tipe unit.
     */
    public BodyPayload setExpiredClaim(long duration, ChronoUnit unit) {
        return withExpiredClaim(FormatUtil.plusDateTime(issuedAtClaim, duration, unit));
    }

    /**
     * Mengset expired JWT time. Parameter expired tidak boleh sebelum issued at.
     *
     * @param expiredClaim Expired yang ingin diset.
     */
    public BodyPayload setExpiredClaim(Date expiredClaim) {
        Instant expInstant = expiredClaim.toInstant();
        Instant iatInstant = issuedAtClaim.toInstant();
        if (!expInstant.isBefore(iatInstant)) {
            return withExpiredClaim(expiredClaim);
        } else {
            throw new IllegalArgumentException("Expired sebelum token dibuat");
        }
    }

    /**
     * Mengset not Before JWT time dengan menambahkan waktu dari issued at.
     *
     * @param duration Panjang durasi.
     * @param unit     Tipe Unit.
     */
    public BodyPayload setNotBeforeClaim(long duration, ChronoUnit unit) {
        return withNotBeforeClaim(FormatUtil.plusDateTime(issuedAtClaim, duration, unit));
    }

    /**
     * Mengset not Before JWT time. Parameter notBefore tidak boleh sebelum issued at.
     *
     * @param notBeforeClaim notBefore yang ingin diset.
     */
    public BodyPayload setNotBeforeClaim(Date notBeforeClaim) {
        Instant expInstant = notBeforeClaim.toInstant();
        Instant iatInstant = issuedAtClaim.toInstant();
        if (!expInstant.isBefore(iatInstant)) {
            return withNotBeforeClaim(notBeforeClaim);
        } else {
            throw new IllegalArgumentException("Expired sebelum token dibuat");
        }
    }

    public BodyPayload addCustomClaims(String key, Object value) {
        this.customClaims.put(key, value);
        return this;
    }

    public BodyPayload setCustomClaims(Claims claims) {
        this.customClaims.putAll(claims);
        return this;
    }

    public boolean hasAudience(String audience) {
        return audienceClaims.contains(audience);
    }

    public Claims buildClaims() {
        Claims claims = Jwts.claims();
        claims.setIssuer(issuerClaim);
        claims.setSubject(subjectClaim);
        claims.setExpiration(expiredClaim);
        claims.setIssuedAt(issuedAtClaim);
        claims.setNotBefore(notBeforeClaim);
        claims.setId(jwtIdClaim);
        claims.put(USER_ID, getSubjectClaim());
        claims.put(TYPE, typeClaim.getCodeName());
        claims.put(ROLES, roleClaims);
        claims.put(AUDIENCE, audienceClaims);
        claims.putAll(customClaims);
        return claims;
    }
}
