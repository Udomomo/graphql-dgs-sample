package com.udomomo.graphql.datafetcher

import com.netflix.dgs.codegen.generated.types.Repository
import com.netflix.dgs.codegen.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import com.udomomo.graphql.query.IssueQuery
import com.udomomo.graphql.query.RepositoryQuery
import com.udomomo.graphql.query.UserQuery

@DgsComponent
class RepositoryDataFetcher(
    private val userQuery: UserQuery,
    private val repositoryQuery: RepositoryQuery,
    private val issueQuery: IssueQuery,
) {
    @DgsQuery
    fun repository(owner: String, name: String): Repository? {
        val userId = userQuery.findByName(owner)?.id ?: return null
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

    @DgsData(parentType = "Repository")
    fun owner(dfe: DgsDataFetchingEnvironment): User? {
        val userId = dfe.getSource<Repository>().owner.id
        val user = userQuery.findById(userId)
        return user?.let {
            User(
                id = { it.id } ,
                name = { it.name },
                projectV2 = { null }
            )
        }
    }
}
