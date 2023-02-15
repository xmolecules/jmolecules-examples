package org.jmolecules.example.archunit

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaPackage
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.Architectures
import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.architecture.cqrs.annotation.CommandDispatcher
import org.jmolecules.architecture.cqrs.annotation.QueryModel
import org.jmolecules.ddd.annotation.AggregateRoot
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.infrastructure.architecture.Query
import org.jmolecules.example.axonframework.infrastructure.architecture.QueryDispatcher

object AxonCqrsEsRules {

  const val COMMAND_DISPATCHER = "Command dispatcher"
  const val QUERY_DISPATCHER = "Query dispatcher"
  const val COMMAND_MODEL = "Command model"
  const val QUERY_MODEL = "Query model"
  const val COMMAND = "Command"
  const val EVENT = "Event"
  const val QUERY = "Query"


  fun ensureAxonCqrsEs(): ArchRule {
    return cqrs()
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
  }


  private fun cqrs(): Architectures.LayeredArchitecture {
    return Architectures
      .layeredArchitecture()
      .consideringOnlyDependenciesInLayers()
      .withOptionalLayers(true)
      .layer(COMMAND_DISPATCHER).definedBy(layerType(CommandDispatcher::class.java))
      .layer(QUERY_DISPATCHER).definedBy(layerType(QueryDispatcher::class.java))
      .layer(COMMAND_MODEL).definedBy(layerType(AggregateRoot::class.java))
      .layer(QUERY_MODEL).definedBy(layerType(QueryModel::class.java))
      .layer(COMMAND).definedBy(layerType(Command::class.java))
      .layer(QUERY).definedBy(layerType(Query::class.java))
      .layer(EVENT).definedBy(layerType(DomainEvent::class.java))
  }


  private fun layerType(annotation: Class<out Annotation?>): IsLayerType? {
    return IsLayerType(annotation)
  }

  private class IsLayerType @JvmOverloads constructor(
    private val annotation: Class<out Annotation>,
    private val exclusions: Collection<Class<out Annotation>> = emptySet()
  ) : DescribedPredicate<JavaClass>(
    "(meta-)annotated with %s or residing in package (meta-)annotated with %s",  //
    annotation.typeName, annotation.typeName
  ) {
    @SafeVarargs
    fun withExclusions(vararg exclusions: Class<out Annotation>): IsLayerType {
      val newExclusions: MutableCollection<Class<out Annotation>> = HashSet(this.exclusions)
      newExclusions.addAll(listOf(*exclusions))
      return IsLayerType(annotation, newExclusions)
    }

    override fun test(type: JavaClass): Boolean {
      return if (exclusions.stream().anyMatch {
          hasDirectOrMetaAnnotation(type, it)
        }) {
        false
      } else {
        (hasDirectOrMetaAnnotation(type, annotation) || hasAnnotationOnPackageOrParent(type.getPackage()))
      }
    }

    private fun hasDirectOrMetaAnnotation(type: JavaClass, annotation: Class<out Annotation>): Boolean {
      return type.isAnnotatedWith(annotation) || type.isMetaAnnotatedWith(annotation)
    }

    private fun hasAnnotationOnPackageOrParent(javaPackage: JavaPackage): Boolean {
      val excluded = exclusions.any {
        javaPackage.isAnnotatedWith(it) || javaPackage.isMetaAnnotatedWith(it)
      }
      return if (excluded) {
        false
      } else {
        if (javaPackage.isAnnotatedWith(annotation) || javaPackage.isMetaAnnotatedWith(annotation)) {
          true
        } else {
          javaPackage.parent
            .map { hasAnnotationOnPackageOrParent(it) }
            .orElse(false)
        }
      }
    }
  }
}
