package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.dao.id.IdTable

object IssueTable: IdTable<String>("issues") {
    override val id = varchar("id", 16).entityId()
    val url = varchar("url", 255)
    val title = varchar("title", 64)
    val closed = integer("closed")
    val number = integer("number")
    val repositoryId = varchar("repository", 16).references(RepositoryTable.id)
}
