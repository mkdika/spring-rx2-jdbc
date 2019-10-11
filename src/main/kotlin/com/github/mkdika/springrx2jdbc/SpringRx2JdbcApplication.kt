package com.github.mkdika.springrx2jdbc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringRx2JdbcApplication

fun main(args: Array<String>) {
	runApplication<SpringRx2JdbcApplication>(*args)
}
