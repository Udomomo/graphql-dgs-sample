package com.udomomo.graphql.datafetcher

import com.netflix.dgs.codegen.generated.types.Issue
import com.netflix.dgs.codegen.generated.types.IssueConnection
import com.netflix.dgs.codegen.generated.types.PageInfo
import com.netflix.dgs.codegen.generated.types.Repository
import com.netflix.dgs.codegen.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.InputArgument
import com.udomomo.graphql.domain.IssueStatus
import com.udomomo.graphql.query.IssueQuery
import com.udomomo.graphql.query.UserQuery

@DgsComponent
class IssueDataFetcher(
    private val issueQuery: IssueQuery,
    private val userQuery: UserQuery,
) {
    // repositoryクエリでissueフィールドが指定されたときに実行される。
    // 指定がなければ実行されない。
    @DgsData(parentType = "Repository")
    fun issue(@InputArgument(name = "number") issueNumber: Int, dfe: DgsDataFetchingEnvironment): IssueDTO? {
        val repository = dfe.getSource<RepositoryDataFetcher.RepositoryDTO>() ?: return null
        return issueQuery.findBy(repository.id, issueNumber)?.let {
            IssueDTO(
                id = it.id,
                title = it.title,
                number = it.number,
                closed = when (it.status) {
                    IssueStatus.OPEN -> false
                    IssueStatus.CLOSED -> true
                },
                repositoryId = repository.id,
                authorId = it.authorId,
            )
        }
    }

    data class IssueDTO(
        val id: String,
        val title: String,
        val number: Int,
        val closed: Boolean,
        val authorId: String,
        val repositoryId: String,
    )

    @DgsData(parentType = "Repository")
    fun issues(
        @InputArgument first: Int?,
        dfe: DgsDataFetchingEnvironment,
    ): IssueConnection? {
        val repository = dfe.getSource<RepositoryDataFetcher.RepositoryDTO>() ?: return null
        val issues = issueQuery.listBy(repository.id, first).map {
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

        return IssueConnection(
            edges = { emptyList() },
            nodes = { issues },
            pageInfo = { PageInfo(
                hasNextPage = { false },
                hasPreviousPage = { false} ,
            ) },
            totalCount = { issues.size },
        )
    }

    @DgsData(parentType = "Issue")
    fun author(dfe: DgsDataFetchingEnvironment): User? {
        val userId = dfe.getSource<IssueDTO>().authorId
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
