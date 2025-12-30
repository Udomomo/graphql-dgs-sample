package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.dao.id.IdTable

object ProjectTable: IdTable<String>("projects") {
    override val id = varchar("id", 16).entityId()
    val title = varchar("title", 64)
    val url = varchar("url", 255)
    val owner = varchar("owner", 16).references(UserTable.id)
}
