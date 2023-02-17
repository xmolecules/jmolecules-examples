package org.jmolecules.example.axonframework.bank.domain.bankaccount.type

import org.jmolecules.ddd.annotation.ValueObject

@JvmInline
@ValueObject
value class ReservedAmount private constructor(val value: Int) {

  companion object {
    fun of(value: Int): ReservedAmount {
      require(value >= 0) { "Amount must be not negative, but it was $value" }
      return ReservedAmount(value)
    }
  }

  override fun toString(): String = "'$value'"
}
