package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "project_cards")
data class ProjectCardEntity(
    @Id
    val id: String,
    @OneToOne(targetEntity = ProjectEntity::class)
    val project: ProjectEntity,
    @OneToOne(targetEntity = IssueEntity::class)
    val issue: IssueEntity?,
    @OneToOne(targetEntity = PullRequestEntity::class)
    val pullRequest: PullRequestEntity?,
) {
    init {
        require((issue != null) || (pullRequest != null)) {
            "Either issue or pullRequest must be non-null"
        }
    }
}
