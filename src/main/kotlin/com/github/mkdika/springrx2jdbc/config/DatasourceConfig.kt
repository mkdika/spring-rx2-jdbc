package com.github.mkdika.springrx2jdbc.config

import org.davidmoten.rx.jdbc.ConnectionProvider
import org.davidmoten.rx.jdbc.Database
import org.davidmoten.rx.jdbc.pool.Pools
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class DatasourceConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun databaseConnection(
        @Value("\${database.url}") databaseUrl: String,
        @Value("\${database.username}") databaseUsername: String,
        @Value("\${database.password}") databasePassword: String,
        @Value("\${database.pool.max-size}") databasePoolMaxSize: Int
    ): Database {
        val connProvider = ConnectionProvider.from(databaseUrl, databaseUsername, databasePassword)
        val pool = Pools.nonBlocking()
            .maxPoolSize(databasePoolMaxSize)
            .connectionProvider(connProvider)
            .build()
        return Database.from(pool)
    }
}