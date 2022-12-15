package org.jmolecules.example.axonframework.core.model.bankaccount

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Represents amount.
 */
@JvmInline
@ValueObject
value class Amount(val value: Int) {
  init {
    require(value > 0) { "Amount must be positive, but it was $value." }
  }
}

