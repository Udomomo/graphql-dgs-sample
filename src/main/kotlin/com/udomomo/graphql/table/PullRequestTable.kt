package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.dao.id.IdTable

object PullRequestTable: IdTable<String>("pull_requests") {
    override val id = varchar("id", 16).entityId()
    val baseRefName = varchar("base_ref_name", 64)
    val closed = integer("closed")
    val headRefName = varchar("head_ref_name", 64)
    val url = varchar("url", 255)
    val number = integer("number")
    val repositoryId = varchar("repository_id", 16).references(RepositoryTable.id)
}