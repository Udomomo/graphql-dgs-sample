package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.Table

object ProjectCardTable : Table() {
    val id = varchar("id", 16).entityId()
    val projectId = varchar("project_id", 16).references(ProjectTable.id)
    val issueId = varchar("content_id", 16).nullable()
    val pullRequestId = varchar("content_id", 16).nullable()
}
