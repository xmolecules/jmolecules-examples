package org.jmolecules.example.axonframework.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class InfrastructureConfiguration {

  @Bean
  @Profile("mem")
  public SagaStore<?> sagaStore() {
    return new InMemorySagaStore();
  }

  @Bean
  @Profile("mem")
  public TokenStore tokeStore() {
    return new InMemoryTokenStore();
  }

  @Bean
  @Profile("mem")
  public EventStorageEngine eventStorageEngine() {
    return new InMemoryEventStorageEngine();
  }

  @Bean
  public Cache bankAccountCache() {
    return new WeakReferenceCache();
  }

  @Bean
  @Qualifier("defaultObjectMapper")
  public ObjectMapper bankObjectMapper() {
    return new ObjectMapper();
  }

}
