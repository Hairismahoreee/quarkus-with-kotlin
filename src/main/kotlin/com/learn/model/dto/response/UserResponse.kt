package com.learn.model.dto.response

import com.learn.model.entity.Role
import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.LocalDateTime

@RegisterForReflection
data class UserResponse (
    var id: String?,
    var email: String?,
    var username: String?,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
    var deletedAt: LocalDateTime?
)
