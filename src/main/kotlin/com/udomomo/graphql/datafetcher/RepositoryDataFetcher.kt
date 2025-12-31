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
) {
    @DgsQuery
    fun repository(owner: String, name: String): RepositoryDTO? {
        val userId = userQuery.findByName(owner)?.id ?: return null
        return repositoryQuery.findBy(name, userId) ?.let {
            RepositoryDTO(
                id = it.id,
                name = it.name ,
                owner = RepositoryDTO.OwnerDTO(
                    id = it.ownerId,
                )
            )
        }
    }

    // owner.nameの解決をchild datafetcherに任せているため、DGSが自動生成するRepository型とは別にDTOを定義して使う。
    // 自動生成されたRepository型を使わなくても、フィールド名が一致していればDGSによってマッピングされる。
    data class RepositoryDTO(
        val id: String,
        val name: String,
        val owner: OwnerDTO,
    ) {
        data class OwnerDTO(
            val id: String,
        )
    }

    @DgsData(parentType = "Repository")
    fun owner(dfe: DgsDataFetchingEnvironment): User? {
        val userId = dfe.getSource<RepositoryDTO>().owner.id
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
