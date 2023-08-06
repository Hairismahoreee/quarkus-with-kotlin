package com.learn.controller.v1.user

import com.learn.core.util.CommonStatic
import com.learn.model.dto.body.UserBody
import com.learn.model.dto.response.UserResponse
import com.learn.repository.user.UserCrudRepo
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import org.jboss.resteasy.reactive.RestPath

@Path(CommonStatic.V1)
class UserCrudController {

    @Inject
    @field: Default
    lateinit var repo: UserCrudRepo

    @GET
    @Path("/protect/users")
    fun get(): List<UserResponse> {
        return repo.getAllUser()
    }

    @GET
    @Path("/protect/users/trash")
    fun getTrashed(): List<UserResponse> {
        return repo.getTrashedUser()
    }

    @POST
    @Transactional
    @Path("/protect/users")
    fun create(body: UserBody): UserResponse {
        return repo.create(body)
    }

    @PUT
    @Transactional
    @Path("/protect/users/{id}")
    fun update(body: UserBody, @RestPath id: String): UserResponse {
        return repo.update(body = body, id = id)
    }

    @DELETE
    @Transactional
    @Path("/protect/users/{id}")
    fun delete(@RestPath id: String) {
        repo.softDelete(id)
    }

    @GET
    @Path("/protect/users/{id}")
    fun get(@RestPath id: String): UserResponse {
        return repo.get(id)
    }

}