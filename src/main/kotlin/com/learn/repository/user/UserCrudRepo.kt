package com.learn.repository.user

import com.learn.model.dto.body.UserBody
import com.learn.model.dto.response.UserResponse
import com.learn.model.entity.User
import com.learn.service.utility.PanacheWithSoftDelete
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime
import java.util.Objects

@ApplicationScoped
class UserCrudRepo: PanacheWithSoftDelete<User> {

    fun getAllUser(): List<UserResponse> {
        val users = getAll()
        return users.map { it.mapToRes() }
    }

    fun getTrashedUser(): List<UserResponse> {
        val users = getTrashed()
        return users.map { it.mapToRes() }
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