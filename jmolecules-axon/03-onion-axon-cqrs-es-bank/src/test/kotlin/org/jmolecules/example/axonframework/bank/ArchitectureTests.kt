package org.jmolecules.example.axonframework.bank

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import org.jmolecules.archunit.ExtendedArchitectureRules
import org.jmolecules.archunit.HexagonalArchitecture
import org.jmolecules.archunit.JMoleculesArchitectureRules

/**
 * Test compliance to hexagonal architecture based on JMolecules annotations.
 */
@AnalyzeClasses(packages = ["org.jmolecules.example.axonframework.bank"])
internal class ArchitectureTests {

  @ArchTest
  fun `validates architecture is hexagonal`(classes: JavaClasses) {
    ExtendedArchitectureRules.ensureHexagonal(setOf("JMolecules")).check(classes)
  }

  @ArchTest
  fun `validates architecture is onion`(classes: JavaClasses) {
    ExtendedArchitectureRules.ensureOnionClassical(setOf("JMolecules")).check(classes)
  }

  @ArchTest
  fun `validates clean architecture by tom hombergs`(classes: JavaClasses) {
    HexagonalArchitecture
      // @formatter:off
      .boundedContext("org.jmolecules.example.axonframework.bank")

      .withDomainLayer("domain")

      .withAdaptersLayer("adapter")
      .incoming("in.rest")
      .outgoing("out.eventbus")
      .outgoing("out.dispatch")
      .and()

      .withApplicationLayer("application")
      .incomingPorts("port.in")
      .services("usecase")
      .services("policy")
      .outgoingPorts("port.out")
      .and()

      .withConfiguration("configuration")
      // @formatter:on
      .check(classes)
  }

  @ArchTest
  fun `validates cqrs-es axon framework rules`(classes: JavaClasses) {
    ExtendedArchitectureRules.ensureAxonCqrsEs(setOf("JMolecules")).check(classes)
  }

}
