package com.github.mkdika.springrx2jdbc.router

import com.github.mkdika.springrx2jdbc.model.Person
import com.github.mkdika.springrx2jdbc.repository.PersonRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicInteger

@Configuration
class AllRouter(
    private val repository: PersonRepository
) {

    @Bean
    fun route() = router {

        GET("/ping") {
            ServerResponse.ok().body(Mono.just("pong!"))
        }
        ("/persons").nest {
            // findAll
            GET("/") { request ->
                ServerResponse.ok()
                    .contentType(APPLICATION_JSON)
                    .body(repository.findAll())
                    .switchIfEmpty(ServerResponse.noContent().build())
            }
            // insert
            POST("/") { request ->
                request.bodyToMono(Person::class.java).flatMap(repository::upsert)
                    .flatMap {
                        ServerResponse.ok()
                            .body(Mono.empty())
                    }
            }
            // update
            PUT("/{id}") { request ->
                val id: String = request.pathVariable("id")
                repository.findById(id)
                    .flatMap {
                        request.bodyToMono(Person::class.java).flatMap { person ->
                            ServerResponse.ok()
                                .body(repository.upsert(person))
                        }
                    }.switchIfEmpty(ServerResponse.notFound().build())
            }
            // findById
            GET("/{id}") { request ->
                val id: String = request.pathVariable("id")
                repository.findById(id)
                    .flatMap { person ->
                        ServerResponse.ok()
                            .contentType(APPLICATION_JSON)
                            .bodyValue(person)
                    }.switchIfEmpty(ServerResponse.notFound().build())
            }
            // delete
            DELETE("/{id}") { request ->
                val id: String = request.pathVariable("id")
                ServerResponse.noContent()
                    .build(repository.delete(id))
            }
        }
    }
}