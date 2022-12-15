package org.jmolecules.example.axonframework.core.model.bankaccount.query

import java.util.*

/**
 * Query model for the bank account retrieving the current balance.
 */
interface BankAccountCurrentBalanceProjection {
  fun query(query: BankAccountCurrentBalanceQuery): Optional<BankAccountCurrentBalance>
}
