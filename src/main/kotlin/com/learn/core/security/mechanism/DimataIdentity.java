package com.learn.core.security.mechanism;

import com.learn.core.security.jwt.tool.JwtTools;
import io.jsonwebtoken.Claims;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.security.Permission;
import java.security.Principal;
import java.util.*;


@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DimataIdentity implements SecurityIdentity {

    public static final String DEFAULT_ROLES = "protect";
    public static final String MAINTAINER_ROLES = "maintainer";

    private final DimataUserPrincipal principal;
    private final Set<String> roles;
    private final boolean anonLogin;
    private final boolean isPermit;

    public static SecurityIdentity loginAsAnon() {
        return new DimataIdentity(null, new HashSet<>(), true, true);
    }

    public static SecurityIdentity loginAsUser(Claims claims) {
        Objects.requireNonNull(claims, "Claims is required");
        var principal = new DimataUserPrincipal(claims);
        var roles = JwtTools.unWrapRoles(claims, DEFAULT_ROLES);
        return new DimataIdentity(principal, roles, false, true);
    }

    public static SecurityIdentity loginAsService(Claims claims) {
        Objects.requireNonNull(claims, "Claims is required");
        var principal = new DimataUserPrincipal(claims);
        var roles = Set.of(MAINTAINER_ROLES);
        return new DimataIdentity(principal, roles, false, true);
    }

    public static SecurityIdentity loginDenied() {
        return new DimataIdentity(null, new HashSet<>(), false, false);
    }

    public static SecurityIdentity publicLogin() {
        return new DimataIdentity(null, new HashSet<>(), false, true);
    }

    //----------- OVERRIDE

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAnonymous() {
        return anonLogin;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    @Override
    public <T extends Credential> T getCredential(Class<T> credentialType) {
        // tidak dipakek
        return null;
    }

    @Override
    public Set<Credential> getCredentials() {
        // tidak dipakek
        return new HashSet<>();
    }

    @Override
    public <T> T getAttribute(String name) {
        // Tidak dipakek
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // Tidak dipakek
        return new HashMap<>();
    }

    @Override
    public Uni<Boolean> checkPermission(Permission permission) {
        return Uni.createFrom().item(isPermit);
    }
}
