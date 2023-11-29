package org.jmolecules.example.axonframework.bank.domain.moneytransfer.type

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Represents a reason for money transfer rejection.
 */
@JvmInline
@ValueObject
value class RejectionReason private constructor(val value: String) {
  companion object {
    fun of(value: String?) = RejectionReason(value ?: "No detailed reason available")
  }
}
