package com.learn.repository.user

import com.learn.model.dto.body.UserBody
import com.learn.model.dto.response.UserResponse
import com.learn.model.entity.User
import com.learn.service.utility.PanacheWithSoftDelete
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class UserCrudRepo: PanacheWithSoftDelete<User, UserResponse> {

    fun getAllUser(): List<UserResponse> {
        return getAll(UserResponse::class.java)
    }

    fun getTrashedUser(): List<UserResponse> {
        return getTrashed(UserResponse::class.java)
    }

    fun create(body: UserBody): UserResponse {
        Objects.requireNonNull(body)

        val user = save(body)
        return user.mapToRes()
    }

    fun update(id: String, body: UserBody): UserResponse {
        Objects.requireNonNull(body)
        Objects.requireNonNull(id)

        val user = save(body, id)
        return user.mapToRes()
    }

    fun get(id: String): UserResponse {
        val user = findById(id)
        return user.mapToRes()
    }

    fun softDelete(id: String) {
        softDelete(id, "User")
    }

    //**** Private
    private fun save(body: UserBody, id: String? = null): User {
        val user = when(id) { null -> User() else -> findById(id) }
        user.email = body.email
        user.username = body.username
        user.password = body.password
        persist(user)

        return user
    }

}