package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "repositories")
data class RepositoryEntity(
    @Id
    val id: String,
    @OneToOne(UserEntity::class)
    val owner: UserEntity,
    val name: String,
    val createdAt: LocalDateTime,
)
