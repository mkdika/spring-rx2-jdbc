package com.github.mkdika.springrx2jdbc.repository

import com.github.mkdika.springrx2jdbc.model.Person
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PersonRepository {

    fun findById(id: String): Mono<Person>
    fun findAll(): Flux<Person>
    fun upsert(person: Person): Mono<Person>
    fun delete(id: String): Mono<Void>
}