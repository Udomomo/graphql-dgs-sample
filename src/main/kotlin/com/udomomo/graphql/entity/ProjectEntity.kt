package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "projects")
data class ProjectEntity(
    @Id
    val id: String,
    val title: String,
    val url: String,
    @OneToOne(UserEntity::class)
    val owner: UserEntity,
)
