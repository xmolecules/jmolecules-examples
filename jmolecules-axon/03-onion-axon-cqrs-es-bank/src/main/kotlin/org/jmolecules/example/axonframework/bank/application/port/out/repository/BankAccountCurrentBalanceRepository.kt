package org.jmolecules.example.axonframework.bank.application.port.out.repository

import org.jmolecules.example.axonframework.bank.domain.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import java.util.*

/**
 * Repository for bank account current balances.
 */
interface BankAccountCurrentBalanceRepository {
  fun findById(id: AccountId): Optional<BankAccountCurrentBalance>
  fun save(entity: BankAccountCurrentBalance)
}
