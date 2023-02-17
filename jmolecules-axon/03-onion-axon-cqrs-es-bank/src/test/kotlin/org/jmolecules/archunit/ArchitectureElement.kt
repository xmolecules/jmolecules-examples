package org.jmolecules.archunit

import com.tngtech.archunit.base.DescribedPredicate.greaterThanOrEqualTo
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.conditions.ArchConditions.containNumberOfElements

import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes

import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses


abstract class ArchitectureElement(
  val basePackage: String
) {

  fun fullQualifiedPackage(relativePackage: String): String {
    return "$basePackage.$relativePackage"
  }

  fun denyDependency(fromPackageName: String, toPackageName: String, classes: JavaClasses) {
    noClasses()
      .that()
      .resideInAPackage(matchAllClassesInPackage(fromPackageName))
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage(matchAllClassesInPackage(toPackageName))
      .check(classes)
  }

  fun denyAnyDependency(
    fromPackages: List<String>, toPackages: List<String>, classes: JavaClasses
  ) {
    for (fromPackage in fromPackages) {
      for (toPackage in toPackages) {
        denyDependency(fromPackage, toPackage, classes)
      }
    }
  }

  fun matchAllClassesInPackage(packageName: String): String {
    return "$packageName.."
  }

  fun denyEmptyPackage(packageName: String) {
    classes()
      .that()
      .resideInAPackage(matchAllClassesInPackage(packageName))
      .should(containNumberOfElements(greaterThanOrEqualTo(1)))
      .check(classesInPackage(packageName))
  }

  private fun classesInPackage(packageName: String): JavaClasses {
    return ClassFileImporter().importPackages(packageName)
  }

  fun denyEmptyPackages(packages: List<String>) {
    for (packageName in packages) {
      denyEmptyPackage(packageName)
    }
  }
}
