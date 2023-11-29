package org.jmolecules.example.axonframework.infrastructure.property

import org.axonframework.common.property.AbstractMethodPropertyAccessStrategy
import org.axonframework.common.property.MethodAccessedProperty
import org.axonframework.common.property.Property
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaGetter

/**
 * Property access strategy for fancy getter names of the inherited properties used by Axon Saga accessing properties of data classes.
 */
class KotlinPropertyAccessStrategy : AbstractMethodPropertyAccessStrategy() {

  companion object {
    private const val METADATA_FQN = "kotlin.Metadata"
  }

  override fun getPriority(): Int = 1

  override fun <T : Any> propertyFor(targetClass: Class<out T>, property: String): Property<T>? {
    if (targetClass.isKotlinClass()) {
      val method = targetClass.kotlin.memberProperties.firstOrNull { it.name == property }?.javaGetter
      if (method != null) {
        return MethodAccessedProperty(method, property)
      }
    }
    return super.propertyFor(targetClass, property)
  }

  override fun getterName(property: String): String = property

  private fun Class<*>.isKotlinClass() = declaredAnnotations.any { it.annotationClass.java.name == METADATA_FQN }
}
