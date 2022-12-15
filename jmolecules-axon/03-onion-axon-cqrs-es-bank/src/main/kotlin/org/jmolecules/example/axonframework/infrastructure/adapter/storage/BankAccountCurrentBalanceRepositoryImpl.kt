package org.jmolecules.example.axonframework.infrastructure.adapter.storage

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.port.storage.BankAccountCurrentBalanceRepository
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class BankAccountCurrentBalanceRepositoryImpl : BankAccountCurrentBalanceRepository {

  private val store: MutableMap<AccountId, BankAccountCurrentBalance> = ConcurrentHashMap()

  override fun findById(id: AccountId): Optional<BankAccountCurrentBalance> {
    return Optional.ofNullable(store[id])
  }

  override fun save(entity: BankAccountCurrentBalance) {
    store[entity.accountId] = entity
  }
}
