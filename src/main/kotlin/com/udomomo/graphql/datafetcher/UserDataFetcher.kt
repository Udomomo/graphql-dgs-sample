package com.udomomo.graphql.datafetcher

import com.netflix.dgs.codegen.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import com.udomomo.graphql.query.UserQuery

@DgsComponent
class UserDataFetcher(
    private val userQuery: UserQuery,
) {
    @DgsQuery
    fun user(name: String): User? {
        val user = userQuery.findByName(name)
        return user?.let {
            User(
                id = { it.id } ,
                name = { it.name },
                projectV2 = { null }
            )
        }
    }
}
