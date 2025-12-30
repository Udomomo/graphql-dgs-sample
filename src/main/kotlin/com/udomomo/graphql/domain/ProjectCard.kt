package com.udomomo.graphql.domain

sealed class ProjectCard{
    abstract val id: String
    abstract val projectId: String

    data class IssueCard(
        override val id: String,
        override val projectId: String,
        val issueId: String,
    ) : ProjectCard()

    data class PullRequestCard(
        override val id: String,
        override val projectId: String,
        val pullRequestId: String,
    ) : ProjectCard()
}
