package org.jmolecules.example.axonframework.bank.adapter.out.readmodel.bankaccount

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.bank.application.port.out.repository.BankAccountCurrentBalanceRepository
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
