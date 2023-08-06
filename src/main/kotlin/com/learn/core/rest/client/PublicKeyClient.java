package com.learn.core.rest.client;

import com.learn.core.security.jwt.tool.PublicKeyWrap;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("api/public/public-key")
@RegisterRestClient(configKey = "public-key-client")
public interface PublicKeyClient {
    
    @GET
    @Path("/latest")
    List<PublicKeyWrap> getLatestPublicKey();

    @GET
    @Path("/{pkId}")
    PublicKeyWrap getPublicKey(@RestPath String pkId);
}
