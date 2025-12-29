package com.udomomo.graphql.table

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.isNotNull

object PullRequestTable: Table("pull_requests") {
    val id = varchar("id", 16).entityId()
    val baseRefName = varchar("base_ref_name", 64).isNotNull()
    val closed = integer("closed").isNotNull()
    val headRefName = varchar("head_ref_name", 64).isNotNull()
    val url = varchar("url", 255).isNotNull()
    val number = integer("number")
    val repositoryId = varchar("repository_id", 16).references(RepositoryTable.id)
}