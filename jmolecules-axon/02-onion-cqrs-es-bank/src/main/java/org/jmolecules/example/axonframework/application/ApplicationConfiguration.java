package org.jmolecules.example.axonframework.application;

import org.jmolecules.example.axonframework.domain.command.model.MoneyTransferIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public MoneyTransferIdGenerator randomGenerator() {
    return MoneyTransferIdGenerator.RANDOM;
  }
}
