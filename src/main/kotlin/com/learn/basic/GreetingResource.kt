package com.learn.basic

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestQuery

@Path("/hello")
class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello from RESTEasy Reactive"

    @GET
    @Path("/greeting")
    fun greeting(@RestQuery name: String): Greeting {
        return Greeting("Hello $name")
    }

    @GET
    @Path("/greeting-with-body")
    fun greetingWithBody(body: GreetingBody): Greeting {
        return Greeting("Hello ${body.body}")
    }
}