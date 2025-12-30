package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.dao.id.IdTable

object UserTable: IdTable<String>("users") {
    override val id = varchar("id", 16).entityId()
    val name = varchar("name", 64)
    val projectV2 = varchar("project_v2", 16).nullable()
}
