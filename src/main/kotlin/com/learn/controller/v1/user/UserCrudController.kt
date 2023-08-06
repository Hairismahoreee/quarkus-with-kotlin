package com.learn.controller.v1.user

import com.learn.model.dto.body.UserBody
import com.learn.model.dto.response.UserResponse
import com.learn.repository.user.UserCrudRepo
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.PATCH
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import org.jboss.resteasy.reactive.RestPath

@Path("/api/v1")
class UserCrudController {

    @Inject
    @field: Default
    lateinit var repo: UserCrudRepo

    @GET
    @Path("/users")
    fun get(): List<UserResponse> {
        return repo.getAllUser()
    }

    @GET
    @Path("/users/trash")
    fun getTrashed(): List<UserResponse> {
        return repo.getTrashedUser()
    }

    @POST
    @Transactional
    @Path("/users")
    fun create(body: UserBody): UserResponse {
        return repo.create(body)
    }

    @PUT
    @Transactional
    @Path("/users/{id}")
    fun update(body: UserBody, @RestPath id: String): UserResponse {
        return repo.update(body = body, id = id)
    }

    @DELETE
    @Transactional
    @Path("/users/{id}")
    fun delete(@RestPath id: String) {
        repo.softDelete(id)
    }

    @GET
    @Path("/users/{id}")
    fun get(@RestPath id: String): UserResponse {
        return repo.get(id)
    }

}