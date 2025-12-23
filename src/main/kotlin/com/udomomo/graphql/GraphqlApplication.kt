package com.udomomo.graphql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlApplication

fun main(args: Array<String>) {
	runApplication<GraphqlApplication>(*args)
}
