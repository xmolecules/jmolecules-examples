package org.jmolecules.example.axonframework.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
import org.jmolecules.example.axonframework.domain.command.service.MoneyTransferIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Configuration
public class InfrastructureConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(InfrastructureConfiguration.class);

  @Bean
  @Profile("mem")
  public EventStorageEngine eventStorageEngine() {
    return new InMemoryEventStorageEngine();
  }

  @Bean
  public SagaStore<?> sagaStore() {
    return new InMemorySagaStore();
  }

  @Bean
  public TokenStore tokeStore() {
    return new InMemoryTokenStore();
  }

  @Bean
  public MoneyTransferIdGenerator randomGenerator() {
    return () -> UUID.randomUUID().toString();
  }

  @Bean
  @Qualifier("defaultObjectMapper")
  public ObjectMapper bankObjectMapper() {
    return new ObjectMapper();
  }


  @Bean
  @Profile("mem")
  public void reportInMem() {
    logger.warn("[INFRASTRUCTURE SETUP] Using in-memory event store and local buses.");
  }

  @Bean
  @Profile("!mem")
  public void reportNotInMem() {
    logger.warn("[INFRASTRUCTURE SETUP] Using Axon Server for bus communication and as event store.");
  }

}
