package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.Table

object ProjectTable: Table("projects") {
    val id = varchar("id", 16).entityId()
    val title = varchar("title", 64)
    val url = varchar("url", 255)
    val owner = varchar("owner", 16).references(UserTable.id)
}
