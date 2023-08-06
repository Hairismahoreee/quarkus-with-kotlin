package com.learn.core.security.mechanism;

import com.learn.core.security.jwt.authentication.AuthenticationHandler;
import com.learn.core.exception.PublicKeyException;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class DimataAuthMechanismImpl {

    @Inject
    AuthenticationHandler authHDL;

    private static final String INTERNAL_HEADER = "dsj-internal-auth";
    private static final String SERVICE_HEADER = "dsj-service-auth";
    
    public Uni<SecurityIdentity> authentication(RoutingContext context) {
        return Uni.createFrom()
            .item(context.request())
            .onItem()
            .transformToUni(t -> {
                if(t.getHeader(SERVICE_HEADER) != null) {
                    return serviceAuth(t);
                }else if(t.getHeader(INTERNAL_HEADER) != null) {
                    return internalAuth(t);
                }else {
                    return Uni.createFrom().optional(Optional.empty());
                }
            });
    }

    private Uni<SecurityIdentity> serviceAuth(HttpServerRequest request) {
        return Uni.createFrom()
            .item(request.getHeader(SERVICE_HEADER))
            .onItem()
            .transform(authHDL::serviceAuth);
    }

    private Uni<SecurityIdentity> internalAuth(HttpServerRequest request) {
        return Uni.createFrom()
            .item(request.getHeader(INTERNAL_HEADER))
            .onItem()
            .transform(authHDL::internalAuth)
            .onFailure(PublicKeyException.class)
            .recoverWithUni(
                Uni.createFrom()
                .item(request.getHeader(INTERNAL_HEADER))
                .onItem()
                .transform(authHDL::internalAuthSecondAttemp)
            );
    }
}
