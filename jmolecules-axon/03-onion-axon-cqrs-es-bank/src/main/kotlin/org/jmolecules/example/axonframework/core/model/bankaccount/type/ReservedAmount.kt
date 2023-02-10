package org.jmolecules.example.axonframework.core.model.bankaccount.type

import org.jmolecules.ddd.annotation.ValueObject

@JvmInline
@ValueObject
value class ReservedAmount private constructor(val value: Int) {

  companion object {
    fun of(value: Int) = ReservedAmount(value)
  }

  init {
    require(value >= 0) { "Amount must be positive or null, but it was $value" }
  }
}
