package com.udomomo.graphql.datafetcher

import com.netflix.dgs.codegen.generated.types.Issue
import com.netflix.dgs.codegen.generated.types.Repository
import com.netflix.dgs.codegen.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import com.udomomo.graphql.domain.IssueStatus
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

    // repositoryクエリでissueフィールドが指定されたときに実行される。
    // 指定がなければ実行されない。
    @DgsData(parentType = "Repository")
    fun issue(@InputArgument(name = "number") issueNumber: Int, dfe: DgsDataFetchingEnvironment): Issue? {
        val repository = dfe.getSource<Repository>() ?: return null
        return issueQuery.findBy(repository.id, issueNumber)?.let {
            Issue(
                id = { it.id },
                title = { it.title },
                number = { it.number },
                closed = { when (it.status) {
                    IssueStatus.OPEN -> false
                    IssueStatus.CLOSED -> true
                } },
            )
        }
    }
}
