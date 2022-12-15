package org.jmolecules.example.axonframework.core.port.storage

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.query.BankAccountCurrentBalance
import java.util.*

/**
 * Repository for bank account current balances.
 */
interface BankAccountCurrentBalanceRepository {

  fun findById(id: AccountId): Optional<BankAccountCurrentBalance>

  fun save(entity: BankAccountCurrentBalance)
}
