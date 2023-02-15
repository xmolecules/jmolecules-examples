package org.jmolecules.example.axonframework.infrastructure.architecture

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class Query(
  val name: String = "",
  val namespace: String = ""
)