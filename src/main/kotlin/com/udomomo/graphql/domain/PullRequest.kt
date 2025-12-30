package com.udomomo.graphql.domain

data class PullRequest(
    val id: String,
    val url: String,
    val baseRefName: String,
    val headRefName: String,
    val status: IssueStatus,
    val number: Int,
    val repositoryId: String,
)