package com.learn.model.dto.body

data class UserBody (
    val email: String? = null,
    val username: String,
    val password: String
)