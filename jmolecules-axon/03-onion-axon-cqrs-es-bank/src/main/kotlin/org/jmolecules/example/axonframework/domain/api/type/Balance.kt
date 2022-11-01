package org.jmolecules.example.axonframework.domain.api.type

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Represents balance.
 */
@JvmInline
@ValueObject
value class Balance(val value: Int) {
    operator fun plus(other: Balance): Balance = Balance(this.value + other.value)
    operator fun plus(other: Amount): Balance = Balance(this.value + other.value)
    operator fun compareTo(other: Balance): Int = this.value.compareTo(other.value)
    operator fun minus(other: Amount): Balance = Balance(this.value - other.value)
}
