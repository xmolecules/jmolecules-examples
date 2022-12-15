package org.jmolecules.example.axonframework.core.model.bankaccount

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Represents account id.
 */
@JvmInline
@ValueObject
value class AccountId(val value: String) {
  init {
    require(value.isNotBlank()) { "Account id must not be empty." }
  }
}
