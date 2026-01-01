package com.udomomo.graphql.dataloader

import com.netflix.graphql.dgs.DgsDataLoader
import com.udomomo.graphql.domain.User
import com.udomomo.graphql.query.UserQuery
import org.dataloader.BatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "authors")
class UserDataLoader(
    private val userQuery: UserQuery,
): BatchLoader<String, User> {
    override fun load(ids: List<String>): CompletionStage<List<User>> =
        CompletableFuture.supplyAsync{ userQuery.listByIds(ids) }
}
