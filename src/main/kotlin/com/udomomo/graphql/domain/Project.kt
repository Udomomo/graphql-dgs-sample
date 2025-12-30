package com.udomomo.graphql.domain

data class Project(
    val id: String,
    val name: String,
    val url: String,
    val ownerId: String,
)
