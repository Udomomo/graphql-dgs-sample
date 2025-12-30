package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.javatime.datetime

object RepositoryTable: IdTable<String>("repositories") {
    override val id = varchar("id", 16).entityId()
    val ownerId = varchar("owner", 16).references(UserTable.id)
    val name = varchar("name", 64)
    val createdAt = datetime(name = "created_at")
}
