package org.jmolecules.example.axonframework.core.model.bankaccount.type

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Represents balance.
 */
@JvmInline
@ValueObject
value class Balance private constructor(val value: Int) {

  companion object {
    fun of(value: Int): Balance = Balance(value)
  }

  operator fun plus(other: Balance): Balance = Balance(this.value + other.value)
  operator fun plus(value: Amount): Balance = Balance(this.value + value.value)
  operator fun minus(value: Amount): Balance = Balance(this.value - value.value)
  operator fun minus(value: ReservedAmount): Balance = Balance(this.value - value.value)
  operator fun compareTo(other: Balance): Int = this.value.compareTo(other.value)

}
