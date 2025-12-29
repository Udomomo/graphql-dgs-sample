package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "pull_requests")
data class PullRequestEntity(
    @Id
    val id: String,
    val baseRefName: String,
    val closed: Int,
    val headRefName: String,
    val url: String,
    val number: Int,
    @OneToOne(RepositoryEntity::class)
    val repository: RepositoryEntity,
) {
    init {
        require(closed == 0 || closed == 1) { "closed must be 0 or 1" }
    }
}
