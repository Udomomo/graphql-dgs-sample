package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "pullrequests")
data class PullRequest(
    @Id
    val id: String,
    val baseRefName: String,
    val closed: Int,
    val headRefName: String,
    val url: String,
    val number: Int,
    @OneToOne(Repository::class)
    val repository: Repository,
) {
    init {
        require(closed == 0 || closed == 1) { "closed must be 0 or 1" }
    }
}
