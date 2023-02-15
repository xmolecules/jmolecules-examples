package org.jmolecules.example.archunit

import com.tngtech.archunit.core.domain.JavaClasses


class HexagonalArchitecture private constructor(basePackage: String) : ArchitectureElement(basePackage) {
  private lateinit var adapters: Adapters
  private lateinit var configurationPackage: String
  private lateinit var applicationLayer: ApplicationLayer
  private val domainPackages: MutableList<String> = mutableListOf()

  companion object {
    @JvmStatic
    fun boundedContext(basePackage: String) = HexagonalArchitecture(basePackage)
  }


  fun withConfiguration(packageName: String): HexagonalArchitecture {
    configurationPackage = fullQualifiedPackage(packageName)
    return this
  }

  fun withDomainLayer(domainPackage: String): HexagonalArchitecture {
    domainPackages.add(fullQualifiedPackage(domainPackage))
    return this
  }

  fun withAdaptersLayer(adaptersPackage: String): Adapters {
    adapters = Adapters(parentContext = this, basePackage = fullQualifiedPackage(adaptersPackage))
    return adapters
  }

  fun withApplicationLayer(applicationPackage: String): ApplicationLayer {
    applicationLayer = ApplicationLayer(parentContext = this, basePackage = fullQualifiedPackage(applicationPackage))
    return applicationLayer
  }


  fun check(classes: JavaClasses) {
    require(this::adapters.isInitialized) { "Adapters must be initialized." }
    require(this::configurationPackage.isInitialized) { "Configuration must be initialized." }
    require(this::applicationLayer.isInitialized) { "Application must be initialized." }
    adapters.doesNotContainEmptyPackages()
    adapters.dontDependOnEachOther(classes)
    adapters.doesNotDependOn(configurationPackage, classes)
    applicationLayer.doesNotContainEmptyPackages()
    applicationLayer.doesNotDependOn(adapters.basePackage, classes)
    applicationLayer.doesNotDependOn(configurationPackage, classes)
    applicationLayer.incomingAndOutgoingPortsDoNotDependOnEachOther(classes)
    domainDoesNotDependOnOtherPackages(classes)
  }

  private fun domainDoesNotDependOnOtherPackages(classes: JavaClasses) {
    denyAnyDependency(
      domainPackages, listOf(adapters.basePackage), classes
    )
    denyAnyDependency(
      domainPackages, listOf(applicationLayer.basePackage), classes
    )
  }
}