package com.learn.service.utility

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.persistence.Entity
import java.time.LocalDateTime

interface PanacheWithSoftDelete<Entity : Any> : PanacheRepositoryBase<Entity, String> {

    fun getAll(): List<Entity> {
        return find("deletedAt is null").list()
    }

    fun getTrashed(): List<Entity> {
        return find("deletedAt is not null").list()
    }

    override fun findById(id: String): Entity {
        return find("id = ?1 and deletedAt is null", id).firstResult() ?: throw Exception("Data not found")
    }

    fun softDelete(id: String, entity: String) {
        update("update $entity set deletedAt = ?1 where id = ?2", LocalDateTime.now(), id)
    }

}