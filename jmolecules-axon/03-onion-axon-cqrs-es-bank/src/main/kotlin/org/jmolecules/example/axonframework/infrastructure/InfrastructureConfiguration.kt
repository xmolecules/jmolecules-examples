package org.jmolecules.example.axonframework.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.modelling.saga.repository.SagaStore
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore
import org.jmolecules.example.axonframework.application.moneytransfer.MoneyTransferIdGenerator
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.*

@Configuration
class InfrastructureConfiguration {
    @Bean
    @Profile("mem")
    fun eventStorageEngine(): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }

    @Bean
    fun sagaStore(): SagaStore<*> {
        return InMemorySagaStore()
    }

    @Bean
    fun tokeStore(): TokenStore {
        return InMemoryTokenStore()
    }

    @Bean
    fun randomGenerator(): MoneyTransferIdGenerator {
        return object : MoneyTransferIdGenerator {
            override fun get(): MoneyTransferId = MoneyTransferId(UUID.randomUUID().toString())
        }
    }

    @Bean
    @Qualifier("defaultObjectMapper")
    fun bankObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .apply {
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            }
    }


    @Bean
    @Profile("mem")
    fun reportInMem() {
        logger.warn("[INFRASTRUCTURE SETUP] Using in-memory event store and local buses.")
    }

    @Bean
    @Profile("!mem")
    fun reportNotInMem() {
        logger.warn("[INFRASTRUCTURE SETUP] Using Axon Server for bus communication and as event store.")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(InfrastructureConfiguration::class.java)
    }
}
