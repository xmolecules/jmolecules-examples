package org.jmolecules.example.axonframework.bank.adapter.out.querymodel.bankaccount

import org.jmolecules.example.axonframework.bank.application.port.out.repository.BankAccountCurrentBalanceRepository
import org.jmolecules.example.axonframework.bank.domain.bankaccount.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Simple in-memory repository implementation.
 */
@Component
class BankAccountCurrentBalanceRepositoryImpl : BankAccountCurrentBalanceRepository {

  private val store: MutableMap<AccountId, BankAccountCurrentBalance> = ConcurrentHashMap()

  override fun findById(accountId: AccountId): Optional<BankAccountCurrentBalance> {
    return Optional.ofNullable(store[accountId])
  }

  override fun save(entity: BankAccountCurrentBalance) {
    store[entity.accountId] = entity
  }
}
