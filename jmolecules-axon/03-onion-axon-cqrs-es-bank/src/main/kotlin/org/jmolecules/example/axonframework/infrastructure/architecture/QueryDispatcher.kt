package org.jmolecules.example.axonframework.infrastructure.architecture

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
annotation class QueryDispatcher(
  val name: String = "",
  val namespace: String = ""
)