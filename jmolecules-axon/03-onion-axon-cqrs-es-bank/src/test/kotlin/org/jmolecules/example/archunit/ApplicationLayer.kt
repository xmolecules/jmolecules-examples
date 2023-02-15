package org.jmolecules.example.archunit

import com.tngtech.archunit.core.domain.JavaClasses


class ApplicationLayer(private val parentContext: HexagonalArchitecture, basePackage: String) : ArchitectureElement(basePackage) {
  private val incomingPortsPackages: MutableList<String> = mutableListOf()
  private val outgoingPortsPackages: MutableList<String> = mutableListOf()
  private val servicePackages: MutableList<String> = mutableListOf()
  private val allPackages by lazy {
    incomingPortsPackages + outgoingPortsPackages + servicePackages
  }

  fun incomingPorts(packageName: String): ApplicationLayer {
    incomingPortsPackages.add(fullQualifiedPackage(packageName))
    return this
  }

  fun outgoingPorts(packageName: String): ApplicationLayer {
    outgoingPortsPackages.add(fullQualifiedPackage(packageName))
    return this
  }

  fun services(packageName: String): ApplicationLayer {
    servicePackages.add(fullQualifiedPackage(packageName))
    return this
  }

  fun and(): HexagonalArchitecture {
    return parentContext
  }

  fun doesNotDependOn(packageName: String, classes: JavaClasses) {
    denyDependency(basePackage, packageName, classes)
  }

  fun incomingAndOutgoingPortsDoNotDependOnEachOther(classes: JavaClasses) {
    denyAnyDependency(incomingPortsPackages, outgoingPortsPackages, classes)
    denyAnyDependency(outgoingPortsPackages, incomingPortsPackages, classes)
  }

  fun doesNotContainEmptyPackages() {
    denyEmptyPackages(allPackages)
  }
}