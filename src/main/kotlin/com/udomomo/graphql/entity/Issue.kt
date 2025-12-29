package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "issues")
data class Issue(
    @Id
    val id: String,
    val url: String,
    val title: String,
    val closed: Int,
    val number: Int,
    @OneToOne(Repository::class)
    val repository: Repository,
    val body: String,
) {
    init {
        require(closed == 0 || closed == 1) { "closed must be 0 or 1" }
    }
}
