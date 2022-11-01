package org.jmolecules.example.axonframework.domain.api.type

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Represents a reason for money transfer rejection.
 */
@JvmInline
@ValueObject
value class Reason(val value: String) {
    companion object {
        fun reasonForMessage(value: String?) = Reason(value ?: "No detailed reason available")
    }
}
