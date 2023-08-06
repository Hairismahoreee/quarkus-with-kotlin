package com.learn.service.utility

import com.learn.core.exception.DataNotFoundException
import com.learn.core.exception.ExceptionCode
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.persistence.Entity
import net.bytebuddy.description.type.TypeDescription.ArrayProjection
import java.time.LocalDateTime

interface PanacheWithSoftDelete<Entity: Any, Projection: Any> : PanacheRepositoryBase<Entity, String> {

    fun getAll(projection: Class<Projection>): List<Projection> {
        return find("deletedAt is null").project(projection).list()
    }

    fun getTrashed(projection: Class<Projection>): List<Projection> {
        return find("deletedAt is not null").project(projection).list()
    }

    override fun findById(id: String): Entity {
        return find("id = ?1 and deletedAt is null", id).firstResult() ?: throw DataNotFoundException(ExceptionCode.DATA_NF)
    }

    fun softDelete(id: String, entity: String) {
        val count = count("id = ?1 and deletedAt is null", id)
        if (count <= 0L) throw DataNotFoundException(ExceptionCode.DATA_NF)
        update("update $entity set deletedAt = ?1 where id = ?2", LocalDateTime.now(), id)
    }

}