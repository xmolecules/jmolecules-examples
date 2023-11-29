package org.jmolecules.example.axonframework.bank.application.port.out.repository

import org.jmolecules.ddd.annotation.Repository
import org.jmolecules.example.axonframework.bank.domain.bankaccount.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import java.util.*

/**
 * Repository for bank account current balances.
 */
@Repository
interface BankAccountCurrentBalanceRepository {
  /**
   * Finds the bank account balance.
   * @param accountId bank account id.
   */
  fun findById(accountId: AccountId): Optional<BankAccountCurrentBalance>

  /**
   * Stores bank account current balance.
   * @param entity current balance of the bank account to store.
   */
  fun save(entity: BankAccountCurrentBalance)
}
