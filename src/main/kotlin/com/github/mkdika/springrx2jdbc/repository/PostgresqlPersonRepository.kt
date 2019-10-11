package com.github.mkdika.springrx2jdbc.repository

import com.github.mkdika.springrx2jdbc.model.Person
import org.davidmoten.rx.jdbc.Database
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Repository
class PostgresqlPersonRepository(
    private val database: Database
) : PersonRepository {

    override fun findById(id: String): Mono<Person> {
        val sql = "SELECT id,firstname,lastname FROM persons WHERE id = ?;"

        val personFlowable =
            database.select(sql)
                .parameters(id)
                .get { rs ->
                    Person(
                        id = rs.getString("id"),
                        firstname = rs.getString("firstname"),
                        lastname = rs.getString("lastname")
                    )
                }
        return Mono.from(personFlowable)
    }

    override fun findAll(): Flux<Person> {
        val sql = "SELECT id,firstname,lastname FROM persons;"

        val personFlowable =
            database.select(sql)
                .get { rs ->
                    Person(
                        id = rs.getString("id"),
                        firstname = rs.getString("firstname"),
                        lastname = rs.getString("lastname")
                    )
                }
        return Flux.from(personFlowable)
    }

    override fun upsert(person: Person): Mono<Person> {

        val id = if (person.id.isNullOrEmpty()) {
            UUID.randomUUID().toString()
        } else {
            person.id
        }

        val sql = """
            INSERT INTO persons(id, firstname, lastname)
                                    VALUES (:id, :firstname, :lastname) 
                                ON CONFLICT (id) DO 
                                UPDATE SET firstname = :firstname, 
                                            lastname = :lastname
        """.trimIndent()
        val sqlSelect = "SELECT id,firstname,lastname FROM persons WHERE id = ?;"

        val personId = database.update(sql)
            .parameter("id", id)
            .parameter("firstname", person.firstname)
            .parameter("lastname", person.lastname)
            .returnGeneratedKeys()
            .getAs(String::class.java)

        val personFlowable =
            database.select(sqlSelect)
                .parameterStream(personId)
                .get { rs ->
                    Person(
                        id = rs.getString("id"),
                        firstname = rs.getString("firstname"),
                        lastname = rs.getString("lastname")
                    )
                }

        return Mono.from(personFlowable)
    }

    override fun delete(id: String): Mono<Void> {
        val sql = "DELETE FROM persons WHERE id = ?"
        val counts = database.update(sql).parameter(id).counts()
        return Flux.from(counts).then()
    }

}