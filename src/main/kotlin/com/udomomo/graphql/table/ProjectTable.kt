package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.isNotNull

object ProjectTable: Table("projects") {
    val id = varchar("id", 16).entityId()
    val title = varchar("title", 64).isNotNull()
    val url = varchar("url", 255).isNotNull()
    val owner = varchar("owner", 16).references(UserTable.id)
}
