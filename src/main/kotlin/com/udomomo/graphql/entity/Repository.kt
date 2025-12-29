package com.udomomo.graphql.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "repositories")
data class Repository(
    @Id
    val id: String,
    @OneToOne(User::class)
    val owner: User,
    val name: String,
    val createdAt: LocalDateTime,
)
