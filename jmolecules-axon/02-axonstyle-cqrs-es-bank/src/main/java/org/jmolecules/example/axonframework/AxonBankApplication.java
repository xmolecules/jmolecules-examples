package org.jmolecules.example.axonframework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class AxonBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(AxonBankApplication.class, args);
  }

}
