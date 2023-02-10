@file: InfrastructureRing

package org.jmolecules.example.axonframework.infrastructure.adapter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.security.AnyTypePermission
import mu.KLogging
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.modelling.saga.repository.SagaStore
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.jmolecules.example.axonframework.core.application.MoneyTransferIdGenerator
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.*

@Configuration
@ComponentScan
class InfrastructureConfiguration {

  companion object : KLogging()

  @Bean
  @Profile("mem")
  fun reportInMem() {
    logger.warn("[INFRASTRUCTURE SETUP] Using in-memory event store and local buses.")
  }

  @Bean
  @Profile("!mem")
  fun reportNotInMem() {
    logger.warn("[INFRASTRUCTURE SETUP] Using Axon Server for bus communication and as Event store.")
  }


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

  @Bean("defaultAxonObjectMapper") // important to use this name to provide correct object mapper for Axon Framework
  fun defaultAxonObjectMapper(): ObjectMapper {
    return jacksonObjectMapper()
      .apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      }
  }

  @Bean
  fun xstream(): XStream = XStream().apply {
    addPermission(AnyTypePermission())
  }

}
