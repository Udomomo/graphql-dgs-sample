package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "projectcards")
data class ProjectCard(
    @Id
    val id: String,
    @OneToOne(targetEntity = Project::class)
    val project: Project,
    @OneToOne(targetEntity = Issue::class)
    val issue: Issue?,
    @OneToOne(targetEntity = PullRequest::class)
    val pullRequest: PullRequest?,
) {
    init {
        require((issue != null) || (pullRequest != null)) {
            "Either issue or pullRequest must be non-null"
        }
    }
}
