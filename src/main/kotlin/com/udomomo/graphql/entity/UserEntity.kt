package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    val id: String,
    val name: String,
    val projectV2: String,
)
