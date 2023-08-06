package com.learn.model.dto.response

import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.LocalDateTime

data class UserResponse(
    val id: String,
    val email: String? = null,
    val username: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null
)
