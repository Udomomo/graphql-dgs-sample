package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "projects")
data class Project(
    @Id
    val id: String,
    val title: String,
    val url: String,
    @OneToOne(User::class)
    val owner: User,
)
