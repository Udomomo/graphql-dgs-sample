package com.udomomo.graphql.domain

data class Issue(
    val id: String,
    val url: String,
    val title: String,
    val number: Int,
    val status: IssueStatus,
)