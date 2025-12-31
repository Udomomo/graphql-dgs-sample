package com.udomomo.graphql.query

import com.udomomo.graphql.domain.Issue
import com.udomomo.graphql.domain.IssueStatus
import com.udomomo.graphql.table.IssueTable
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.select
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class IssueQuery {
    fun findBy(repositoryId: String, number: Int) =
        IssueTable.select(
            IssueTable.id,
            IssueTable.title,
            IssueTable.url,
            IssueTable.closed,
            IssueTable.number,
            IssueTable.authorId,
            IssueTable.repositoryId,
        ).where {
            IssueTable.repositoryId.eq(repositoryId)
                .and(IssueTable.number.eq(number))
        }.firstOrNull()?.let {
            Issue(
                id = it[IssueTable.id].value,
                title = it[IssueTable.title],
                url = it[IssueTable.url],
                number = it[IssueTable.number],
                authorId = it[IssueTable.authorId],
                repositoryId = it[IssueTable.repositoryId],
                status = when (it[IssueTable.closed]) {
                    0 -> IssueStatus.OPEN
                    1 -> IssueStatus.CLOSED
                    else -> throw IllegalStateException(
                        "Invalid closed value | issueId=${it[IssueTable.id].value}, closed=${it[IssueTable.closed]}"
                    )
                }
            )
        }

    fun listBy(
        repositoryId: String,
        first: Int? = null,
    ): List<Issue> =
        IssueTable.select(
            IssueTable.id,
            IssueTable.title,
            IssueTable.url,
            IssueTable.closed,
            IssueTable.number,
            IssueTable.authorId,
            IssueTable.repositoryId,
        ).where {
            IssueTable.repositoryId.eq(repositoryId)
        }.also {
            if (first != null) {
                it.limit(first)
            }
        }
        .map {
            Issue(
                id = it[IssueTable.id].value,
                title = it[IssueTable.title],
                url = it[IssueTable.url],
                number = it[IssueTable.number],
                authorId = it[IssueTable.authorId],
                repositoryId = it[IssueTable.repositoryId],
                status = when (it[IssueTable.closed]) {
                    0 -> IssueStatus.OPEN
                    1 -> IssueStatus.CLOSED
                    else -> throw IllegalStateException(
                        "Invalid closed value | issueId=${it[IssueTable.id].value}, closed=${it[IssueTable.closed]}"
                    )
                }
            )
        }
}
