package org.jmolecules.example.archunit

import com.tngtech.archunit.core.domain.JavaClasses


class Adapters(private val parentContext: HexagonalArchitecture, basePackage: String) : ArchitectureElement(basePackage) {
  private val incomingAdapterPackages: MutableList<String> = mutableListOf()
  private val outgoingAdapterPackages: MutableList<String> = mutableListOf()
  private val allAdapters by lazy {
    incomingAdapterPackages + outgoingAdapterPackages
  }

  fun outgoing(packageName: String): Adapters {
    incomingAdapterPackages.add(fullQualifiedPackage(packageName))
    return this
  }

  fun incoming(packageName: String): Adapters {
    outgoingAdapterPackages.add(fullQualifiedPackage(packageName))
    return this
  }

  fun and(): HexagonalArchitecture {
    return parentContext
  }

  fun dontDependOnEachOther(classes: JavaClasses) {
    for (adapter1 in allAdapters) {
      for (adapter2 in allAdapters) {
        if (adapter1 != adapter2) {
          denyDependency(adapter1, adapter2, classes)
        }
      }
    }
  }

  fun doesNotDependOn(packageName: String, classes: JavaClasses) {
    denyDependency(basePackage, packageName, classes)
  }

  fun doesNotContainEmptyPackages() {
    denyEmptyPackages(allAdapters)
  }
}