package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.isNotNull

object UserTable: Table("users") {
    val id = varchar("id", 16).entityId()
    val name = varchar("name", 64).isNotNull()
    val projectV2 = varchar("project_v2", 16)
}
