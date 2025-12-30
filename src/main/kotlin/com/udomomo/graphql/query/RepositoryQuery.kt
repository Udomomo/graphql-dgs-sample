package com.udomomo.graphql.query

import com.udomomo.graphql.domain.Repository
import com.udomomo.graphql.table.RepositoryTable
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.select
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RepositoryQuery {
    fun findBy(name: String, ownerId: String): Repository? =
        RepositoryTable.select(
            RepositoryTable.id,
            RepositoryTable.name,
            RepositoryTable.ownerId,
        ).where {
            RepositoryTable.name.eq(name)
                .and(RepositoryTable.ownerId.eq(ownerId))
        }.firstOrNull()?.let {
            return Repository(
                id = it[RepositoryTable.id].value,
                name = it[RepositoryTable.name],
                ownerId = it[RepositoryTable.ownerId],
            )
        }
}
