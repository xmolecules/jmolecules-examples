package org.jmolecules.example.axonframework.core.port.out.repository

import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import java.util.*

/**
 * Repository for bank account current balances.
 */
interface BankAccountCurrentBalanceRepository {
  fun findById(id: AccountId): Optional<BankAccountCurrentBalance>
  fun save(entity: BankAccountCurrentBalance)
}
