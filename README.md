# GraphQL DGS Sample
- DGSでGraphQL APIを構築するサンプルプロジェクトです。

# 構成
## バックエンド
- Spring Boot + DGS Framework

## スキーマ
- GraphQL schema: `src/main/resources/schema/schema.graphqls`
- DB schema: `src/main/resources/db/schema.sql`
- 初期投入データ: `src/main/resources/db/data.sql`

※ GraphQLスキーマ・DBスキーマは、[Goで学ぶGraphQLサーバーサイド入門](https://zenn.dev/hsaki/books/golang-graphql)のものを利用しています。

# セットアップ
- 起動: `./gradlew bootRun`
- GraphQLコード生成のみ: `./gradlew generateJava`

# コンソール
- h2 console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:graphql`
  - User Name: `sa`
  - Password: (blank)
- GraphiQL: `http://localhost:8080/graphiql`
