package com.udomomo.graphql.query


import com.udomomo.graphql.domain.User
import com.udomomo.graphql.table.UserTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.select
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserQuery {
    fun findByName(name: String): User? =
        UserTable.select(
            UserTable.id,
            UserTable.name,
        ).where {
            UserTable.name.eq(name)
        }.firstOrNull()?.let {
            User(
                id = it[UserTable.id].value,
                name = it[UserTable.name],
            )
        }

    fun findById(id: String): User? =
        UserTable.select(
            UserTable.id,
            UserTable.name,
        ).where {
            UserTable.id.eq(id)
        }.firstOrNull()?.let {
            User(
                id = it[UserTable.id].value,
                name = it[UserTable.name],
            )
        }
}