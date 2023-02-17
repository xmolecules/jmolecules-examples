package org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer

import org.jmolecules.example.axonframework.bank.application.port.out.repository.MoneyTransferSummaryRepository
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.BankAccountMoneyTransfer
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Simple in memory implementation.
 */
@Component
class MoneyTransferSummaryRepositoryImpl : MoneyTransferSummaryRepository {

  private val store: MutableMap<MoneyTransferId, BankAccountMoneyTransfer> = ConcurrentHashMap()

  override fun findById(id: MoneyTransferId): Optional<BankAccountMoneyTransfer> {
    return Optional.ofNullable(store[id])
  }

  override fun findByAccountId(accountId: AccountId): List<BankAccountMoneyTransfer> {
    return store.values
      .filter { it.sourceAccountId == accountId || it.targetAccountId == accountId }
      .toList()
  }

  override fun findAll(): List<BankAccountMoneyTransfer> {
    return store.values.toList()
  }

  override fun save(entity: BankAccountMoneyTransfer) {
    store[entity.moneyTransferId] = entity
  }
}
