package com.udomomo.graphql.datafetcher

import com.netflix.dgs.codegen.generated.types.Repository
import com.netflix.dgs.codegen.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.udomomo.graphql.query.RepositoryQuery
import com.udomomo.graphql.query.UserQuery

@DgsComponent
class RepositoryDataFetcher(
    private val userQuery: UserQuery,
    private val repositoryQuery: RepositoryQuery,
) {
    @DgsQuery
    fun repository(owner: String, name: String): Repository? {
        val userId = userQuery.findBy(owner)?.id ?: return null
        return repositoryQuery.findBy(name, userId) ?.let {
            Repository(
                id = { it.id },
                name = { it.name },
                owner = { User(
                    id = { userId },
                    name = { owner },
                ) },
            )
        }
    }
}
