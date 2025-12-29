plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.10-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
    // 最新版を使うとCOMPILE_INCREMENTAL_WITH_ARTIFACT_TRANSFORMでビルドが失敗するため、やむなくバージョンを下げている
    id("com.netflix.dgs.codegen") version "7.0.3"
    kotlin("plugin.jpa") version "2.2.21"
}

group = "com.udomomo"
version = "0.0.1-SNAPSHOT"
description = "GraphQL DGS Sample"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["netflixDgsVersion"] = "10.4.0"
val exposedVersion = "1.0.0-rc-4"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:$exposedVersion")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Kotlin向けのコード生成を行う実験的な機能。
tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    language = "kotlin"
    generateClient = true
    // GraphQLのクエリリクエストでは、必要なプロパティしか指定されないため、本来はクエリのレスポンスのプロパティは全てnullableであるべき。
    // しかし型レベルでnullableを区別するKotlinではそれだと不便なので、レスポンスのプロパティがnon-nullableであるかのごとく扱えるようなコード生成をする。
    generateKotlinNullableClasses = true
    generateKotlinClosureProjections = true
}
