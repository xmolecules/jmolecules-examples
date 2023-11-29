package org.jmolecules.archunit

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaPackage
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.Architectures
import org.jmolecules.architecture.cqrs.Command
import org.jmolecules.architecture.cqrs.CommandDispatcher
import org.jmolecules.architecture.cqrs.QueryModel
import org.jmolecules.architecture.hexagonal.*
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing
import org.jmolecules.architecture.onion.classical.DomainModelRing
import org.jmolecules.architecture.onion.classical.DomainServiceRing
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.jmolecules.architecture.onion.simplified.ApplicationRing
import org.jmolecules.architecture.onion.simplified.DomainRing
import org.jmolecules.ddd.annotation.AggregateRoot
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.infrastructure.architecture.Query
import org.jmolecules.example.axonframework.infrastructure.architecture.QueryDispatcher

object ExtendedArchitectureRules {

  const val APPLICATION = "Application"

  const val COMMAND_DISPATCHER = "Command dispatcher"
  const val QUERY_DISPATCHER = "Query dispatcher"
  const val COMMAND_MODEL = "Command model"
  const val QUERY_MODEL = "Query model"
  const val COMMAND = "Command"
  const val EVENT = "Event"
  const val QUERY = "Query"

  const val ONION_CLASSICAL_DOMAIN_MODEL = "Domain model"
  const val ONION_CLASSICAL_DOMAIN_SERVICE = "Domain service"
  const val ONION_CLASSICAL_APPLICATION = "Application"
  const val ONION_CLASSICAL_INFRASTRUCTURE = "Infrastructure"

  const val ONION_SIMPLE_DOMAIN = "Domain"
  const val ONION_SIMPLE_APPLICATION = "Application"
  const val ONION_SIMPLE_INFRASTRUCTURE = "Infrastructure"

  const val HEXAGONAL_APPLICATION = "Application"
  const val HEXAGONAL_PORT = "Port"
  const val HEXAGONAL_PORT_UNQUALIFIED = "Port (unqualified)"
  const val HEXAGONAL_PRIMARY_PORT = "Primary port"
  const val HEXAGONAL_SECONDARY_PORT = "Secondary port"
  const val HEXAGONAL_ADAPTER = "Adapter"
  const val HEXAGONAL_ADAPTER_UNQUALIFIED = "Adapter (unqualified)"
  const val HEXAGONAL_PRIMARY_ADAPTER = "Primary adapter"
  const val HEXAGONAL_SECONDARY_ADAPTER = "Secondary adapter"


  fun ensureAxonCqrsEs(markerSimpleClassNames: Set<String> = emptySet()): ArchRule {
    return cqrs(markerSimpleClassNames).ensureAxonRulesRules()
  }

  /**
   * ArchUnit [ArchRule] defining a simplified variant of the Onion Architecture.
   *
   * @return will never be null.
   * @see ApplicationRing
   *
   * @see DomainRing
   *
   * @see InfrastructureRing
   */
  fun ensureOnionSimple(markerSimpleClassNames: Set<String> = emptySet()): ArchRule {
    return onionArchitectureSimple(markerSimpleClassNames).ensureOnionSimpleRules()
  }

  /**
   * ArchUnit [ArchRule] defining the classic Onion Architecture.
   *
   * @return will never be null.
   * @see ApplicationServiceRing
   *
   * @see DomainServiceRing
   *
   * @see DomainModelRing
   *
   * @see org.jmolecules.architecture.onion.classical.InfrastructureRing
   */
  fun ensureOnionClassical(markerSimpleClassNames: Set<String> = emptySet()): ArchRule {
    return onionArchitecture(markerSimpleClassNames).ensureOnionClassicRules()
  }

  fun ensureHexagonal(markerSimpleClassNames: Set<String> = emptySet()): ArchRule {
    return hexagonalArchitecture(markerSimpleClassNames).ensureHexagonalRules()
  }

  fun Architectures.LayeredArchitecture.ensureHexagonalRules() =
    this.whereLayer(HEXAGONAL_PRIMARY_PORT)
      .mayOnlyBeAccessedByLayers(
        APPLICATION,
        HEXAGONAL_PORT_UNQUALIFIED,
        HEXAGONAL_ADAPTER_UNQUALIFIED,
        HEXAGONAL_PRIMARY_ADAPTER
      )
      .whereLayer(HEXAGONAL_SECONDARY_PORT)
      .mayOnlyBeAccessedByLayers(
        APPLICATION,
        HEXAGONAL_PORT_UNQUALIFIED,
        HEXAGONAL_ADAPTER_UNQUALIFIED,
        HEXAGONAL_SECONDARY_ADAPTER
      )
      .whereLayer(HEXAGONAL_PORT)
      .mayOnlyBeAccessedByLayers(APPLICATION, HEXAGONAL_ADAPTER)
      .whereLayer(HEXAGONAL_ADAPTER_UNQUALIFIED)
      .mayOnlyBeAccessedByLayers(
        HEXAGONAL_PRIMARY_ADAPTER,
        HEXAGONAL_SECONDARY_ADAPTER
      )
      .whereLayer(HEXAGONAL_PRIMARY_ADAPTER)
      .mayOnlyBeAccessedByLayers(HEXAGONAL_ADAPTER_UNQUALIFIED)
      .whereLayer(HEXAGONAL_SECONDARY_ADAPTER)
      .mayOnlyBeAccessedByLayers(HEXAGONAL_ADAPTER_UNQUALIFIED)
      .whereLayer(APPLICATION)
      .mayNotBeAccessedByAnyLayer()

  fun Architectures.LayeredArchitecture.ensureOnionClassicRules() =
    this
      .whereLayer(ONION_CLASSICAL_INFRASTRUCTURE)
      .mayNotBeAccessedByAnyLayer()
      .whereLayer(ONION_CLASSICAL_APPLICATION)
      .mayOnlyBeAccessedByLayers(ONION_CLASSICAL_INFRASTRUCTURE)
      .whereLayer(ONION_CLASSICAL_DOMAIN_SERVICE)
      .mayOnlyBeAccessedByLayers(
        ONION_CLASSICAL_APPLICATION,
        ONION_CLASSICAL_INFRASTRUCTURE
      )
      .whereLayer(ONION_CLASSICAL_DOMAIN_MODEL)
      .mayOnlyBeAccessedByLayers(
        ONION_CLASSICAL_DOMAIN_SERVICE,
        ONION_CLASSICAL_DOMAIN_SERVICE,
        ONION_CLASSICAL_APPLICATION,
        ONION_CLASSICAL_INFRASTRUCTURE
      )

  fun Architectures.LayeredArchitecture.ensureOnionSimpleRules() =
    this.whereLayer(ONION_SIMPLE_INFRASTRUCTURE)
      .mayNotBeAccessedByAnyLayer()
      .whereLayer(ONION_SIMPLE_APPLICATION)
      .mayOnlyBeAccessedByLayers(ONION_SIMPLE_INFRASTRUCTURE)
      .whereLayer(ONION_SIMPLE_DOMAIN)
      .mayOnlyBeAccessedByLayers(
        ONION_SIMPLE_APPLICATION,
        ONION_SIMPLE_INFRASTRUCTURE
      )

  fun Architectures.LayeredArchitecture.ensureAxonRulesRules() =
    this
      .whereLayer(COMMAND_MODEL)
      .mayNotBeAccessedByAnyLayer()

      .whereLayer(QUERY_MODEL)
      .mayNotBeAccessedByAnyLayer()

      .whereLayer(COMMAND)
      .mayOnlyBeAccessedByLayers(COMMAND_MODEL, COMMAND_DISPATCHER)

      .whereLayer(QUERY)
      .mayOnlyBeAccessedByLayers(QUERY_MODEL, QUERY_DISPATCHER)

      .whereLayer(EVENT)
      .mayOnlyBeAccessedByLayers(QUERY_MODEL, COMMAND_MODEL)


  fun onionArchitecture(markerSimpleClassNames: Set<String> = emptySet()): Architectures.LayeredArchitecture {
    return Architectures.layeredArchitecture()
      .consideringOnlyDependenciesInLayers()
      .withOptionalLayers(true)
      .layer(ONION_CLASSICAL_INFRASTRUCTURE)
      .definedBy(layerType(InfrastructureRing::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(ONION_CLASSICAL_APPLICATION).definedBy(
        layerType(
          ApplicationServiceRing::class.java, markerSimpleClassNames = markerSimpleClassNames
        )
      )
      .layer(ONION_CLASSICAL_DOMAIN_SERVICE).definedBy(
        layerType(
          DomainServiceRing::class.java, markerSimpleClassNames = markerSimpleClassNames
        )
      )
      .layer(ONION_CLASSICAL_DOMAIN_MODEL).definedBy(
        layerType(
          DomainModelRing::class.java, markerSimpleClassNames = markerSimpleClassNames
        )
      )
  }

  fun onionArchitectureSimple(markerSimpleClassNames: Set<String> = emptySet()): Architectures.LayeredArchitecture {
    return Architectures.layeredArchitecture()
      .consideringOnlyDependenciesInLayers()
      .withOptionalLayers(true)
      .layer(ONION_SIMPLE_INFRASTRUCTURE)
      .definedBy(
        layerType(
          org.jmolecules.architecture.onion.simplified.InfrastructureRing::class.java,
          markerSimpleClassNames = markerSimpleClassNames
        )
      )
      .layer(ONION_SIMPLE_APPLICATION).definedBy(
        layerType(
          ApplicationRing::class.java, markerSimpleClassNames = markerSimpleClassNames
        )
      )
      .layer(ONION_SIMPLE_DOMAIN)
      .definedBy(layerType(DomainRing::class.java, markerSimpleClassNames = markerSimpleClassNames))
  }

  fun hexagonalArchitecture(markerSimpleClassNames: Set<String> = emptySet()): Architectures.LayeredArchitecture {
    return Architectures.layeredArchitecture()
      .consideringOnlyDependenciesInLayers()
      .withOptionalLayers(true)
      .layer(HEXAGONAL_APPLICATION)
      .definedBy(layerType(Application::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(HEXAGONAL_PORT).definedBy(layerType(Port::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(HEXAGONAL_PORT_UNQUALIFIED).definedBy(
        layerType(
          Port::class.java,
          markerSimpleClassNames = markerSimpleClassNames
        ).withExclusions(PrimaryPort::class.java, SecondaryPort::class.java)
      )
      .layer(HEXAGONAL_PRIMARY_PORT)
      .definedBy(layerType(PrimaryPort::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(HEXAGONAL_SECONDARY_PORT)
      .definedBy(layerType(SecondaryPort::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(HEXAGONAL_ADAPTER)
      .definedBy(layerType(Adapter::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(HEXAGONAL_ADAPTER_UNQUALIFIED).definedBy(
        layerType(Adapter::class.java, markerSimpleClassNames = markerSimpleClassNames).withExclusions(
          PrimaryAdapter::class.java,
          SecondaryAdapter::class.java
        )
      )
      .layer(HEXAGONAL_PRIMARY_ADAPTER)
      .definedBy(layerType(PrimaryAdapter::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(HEXAGONAL_SECONDARY_ADAPTER)
      .definedBy(layerType(SecondaryAdapter::class.java, markerSimpleClassNames = markerSimpleClassNames))
  }


  fun cqrs(markerSimpleClassNames: Set<String> = emptySet()): Architectures.LayeredArchitecture {
    return Architectures
      .layeredArchitecture()
      .consideringOnlyDependenciesInLayers()
      .withOptionalLayers(true)
      .layer(COMMAND_DISPATCHER)
      .definedBy(layerType(CommandDispatcher::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(QUERY_DISPATCHER)
      .definedBy(layerType(QueryDispatcher::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(COMMAND_MODEL)
      .definedBy(layerType(AggregateRoot::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(QUERY_MODEL).definedBy(layerType(QueryModel::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(COMMAND).definedBy(layerType(Command::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(QUERY).definedBy(layerType(Query::class.java, markerSimpleClassNames = markerSimpleClassNames))
      .layer(EVENT).definedBy(layerType(DomainEvent::class.java, markerSimpleClassNames = markerSimpleClassNames))
  }

  fun layerType(annotation: Class<out Annotation>, markerSimpleClassNames: Set<String> = emptySet()): IsLayerType {
    return IsLayerType(annotation = annotation, markerSimpleClassNames = markerSimpleClassNames)
  }

  class IsLayerType @JvmOverloads constructor(
    private val annotation: Class<out Annotation>,
    private val exclusions: Set<Class<out Annotation>> = emptySet(),
    private val markerSimpleClassNames: Set<String> = emptySet()
  ) : DescribedPredicate<JavaClass>(
    "(meta-)annotated with %s or residing in package (meta-)annotated with %s",  //
    annotation.typeName, annotation.typeName
  ) {
    @SafeVarargs
    fun withExclusions(vararg exclusions: Class<out Annotation>): IsLayerType {
      return IsLayerType(annotation, this.exclusions.toMutableSet() + setOf(*exclusions))
    }

    override fun test(type: JavaClass): Boolean {
      return if (exclusions.any { type.hasDirectOrMetaAnnotation(it) }) {
        false
      } else {
        type.hasDirectOrMetaAnnotation(annotation)
            || type.getPackage().hasAnnotationOnPackageOrParent()
            || type.getPackage().hasAnnotatedMarkerTypeInPackageOrParent()
      }
    }

    private fun JavaPackage.hasAnnotatedMarkerTypeInPackageOrParent(): Boolean {
      return if (markerSimpleClassNames
          .filter { simpleName -> this.containsClassWithSimpleName(simpleName) }
          .map { simpleName -> this.getClassWithSimpleName(simpleName) }
          .any { clazz -> clazz.hasDirectOrMetaAnnotation(annotation) }
      ) {
        true
      } else {
        this.parent
          .map { it.hasAnnotatedMarkerTypeInPackageOrParent() }
          .orElse(false)
      }
    }

    private fun JavaClass.hasDirectOrMetaAnnotation(annotation: Class<out Annotation>): Boolean {
      return this.isAnnotatedWith(annotation) || this.isMetaAnnotatedWith(annotation)
    }

    private fun JavaPackage.hasAnnotationOnPackageOrParent(): Boolean {
      return if (exclusions.any { this.isAnnotatedWith(it) || this.isMetaAnnotatedWith(it) }) {
        false
      } else {
        if (this.isAnnotatedWith(annotation) || this.isMetaAnnotatedWith(annotation)) {
          true
        } else {
          this.parent
            .map { it.hasAnnotationOnPackageOrParent() }
            .orElse(false)
        }
      }
    }
  }
}
