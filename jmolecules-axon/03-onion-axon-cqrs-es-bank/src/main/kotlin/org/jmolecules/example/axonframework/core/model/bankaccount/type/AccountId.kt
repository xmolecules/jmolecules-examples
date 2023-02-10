package org.jmolecules.example.axonframework.core.model.bankaccount.type

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Represents account id.
 */
@JvmInline
@ValueObject
value class AccountId private constructor(val value: String) {
  companion object {
    fun of(value: String): AccountId {
      require(value.isNotBlank()) { "Account id must not be empty." }
      return AccountId(value)
    }
  }
}
