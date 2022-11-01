package org.jmolecules.example.axonframework.domain.api.type

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
