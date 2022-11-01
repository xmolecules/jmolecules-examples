package org.jmolecules.example.axonframework.domain.model.bank

import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalance
import java.util.*

/**
 * Query model for the bank account retrieving the current balance.
 */
interface BankAccountCurrentBalanceProjection {
    fun query(query: BankAccountCurrentBalanceQuery): Optional<BankAccountCurrentBalance>
}
