package com.udomomo.graphql.datafetcher

import com.netflix.dgs.codegen.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
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
                ownerId = it.ownerId,
            )
        }
    }

    // owner.nameの解決をchild datafetcherに任せているため、DGSが自動生成するRepository型とは別にDTOを定義して使う。
    // 自動生成されたRepository型を使わなくても、フィールド名が一致していればDGSによってマッピングされる。
    // また、DTOに不要なフィールドがあっても問題ない。ownerIdはchild datafetcherで使うために用意している。
    data class RepositoryDTO(
        val id: String,
        val name: String,
        val ownerId: String,
    )

    @DgsData(parentType = "Repository")
    fun owner(dfe: DgsDataFetchingEnvironment): User? {
        val userId = dfe.getSource<RepositoryDTO>().ownerId
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
