@file: InfrastructureRing

package org.jmolecules.example.axonframework.infrastructure.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.modelling.saga.repository.SagaStore
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferStatus
import org.jmolecules.example.axonframework.infrastructure.jackson.KotlinTypeInfo
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.*

@Configuration
class InfrastructureConfiguration {

  companion object : KLogging() {
    val DEFAULT_OBJECT_MAPPER = jacksonObjectMapper()
      .apply {
        addMixIn(MoneyTransferStatus::class.java, KotlinTypeInfo::class.java)
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

  @Bean("defaultAxonObjectMapper") // important to use this name to provide correct object mapper for Axon Framework
  fun defaultAxonObjectMapper(): ObjectMapper {
    return DEFAULT_OBJECT_MAPPER
  }

  @Bean
  fun defaultOMCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
    return Jackson2ObjectMapperBuilderCustomizer { builder ->
      builder
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .featuresToDisable(
          SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        )
    }
  }

//  @Bean
//  fun xstream(): XStream = XStream().apply {
//    addPermission(AnyTypePermission())
//  }

}
