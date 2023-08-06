package com.learn.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "roles")
class Role {

    @Id
    @Column(name = "role_id", length = 36)
    val id: String = UUID.randomUUID().toString()

    @Column(length = 50)
    lateinit var name: String

    @Column(name = "created_at")
    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var users: Set<User> = HashSet<User>()
}