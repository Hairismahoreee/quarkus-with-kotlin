package com.learn.model.entity

import com.learn.model.dto.response.UserResponse
import com.learn.service.utility.PasswordHasher
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

@Entity
@Table(name = "users")
class User {

    @Id
    @Column(name = "user_id", length = 36)
    val id: String = UUID.randomUUID().toString()

    @Column(length = 50)
    var email: String? = null

    @Column(length = 50)
    var username: String = ""
        set(value) {
            field = value.lowercase().replace(" ", "")
        }

    @Column(length = 191)
    var password: String = ""
        set(value) {
            val generator = PasswordHasher()
            field = generator.generateHash(value)
        }


    @Column(name = "created_at")
    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    var roles: Set<Role> = HashSet<Role>()

    //**** Map to response
    fun mapToRes(): UserResponse {
        return UserResponse(
            id = id,
            username = username,
            email = email,
            updatedAt = updatedAt,
            createdAt = createdAt,
            deletedAt = deletedAt
        )
    }
}