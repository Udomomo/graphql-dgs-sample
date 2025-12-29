package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.isNotNull

object IssueTable: IdTable<String>("issues") {
    override val id = varchar("id", 16).entityId()
    val url = varchar("url", 255).isNotNull()
    val title = varchar("title", 64).isNotNull()
    val closed = integer("closed").isNotNull()
    val number = integer("number").isNotNull()
    val repositoryId = varchar("repository_id", 16).references(RepositoryTable.id)
}
