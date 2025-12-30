package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.Table

object PullRequestTable: Table("pull_requests") {
    val id = varchar("id", 16).entityId()
    val baseRefName = varchar("base_ref_name", 64)
    val closed = integer("closed")
    val headRefName = varchar("head_ref_name", 64)
    val url = varchar("url", 255)
    val number = integer("number")
    val repositoryId = varchar("repository_id", 16).references(RepositoryTable.id)
}