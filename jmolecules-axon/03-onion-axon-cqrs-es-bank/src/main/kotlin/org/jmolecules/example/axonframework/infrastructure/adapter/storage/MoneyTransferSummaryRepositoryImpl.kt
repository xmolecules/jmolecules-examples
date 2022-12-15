package org.jmolecules.example.axonframework.infrastructure.adapter.storage

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.port.storage.MoneyTransferSummaryRepository
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class MoneyTransferSummaryRepositoryImpl : MoneyTransferSummaryRepository {

  private val store: MutableMap<MoneyTransferId, MoneyTransferSummary> = ConcurrentHashMap()

  override fun findById(id: MoneyTransferId): Optional<MoneyTransferSummary> {
    return Optional.ofNullable(store[id])
  }

  override fun findByAccountId(accountId: AccountId): List<MoneyTransferSummary> {
    return store.values.stream()
      .filter { it: MoneyTransferSummary -> it.sourceAccountId == accountId || it.targetAccountId == accountId }
      .toList()
  }

  override fun findAll(): List<MoneyTransferSummary> {
    return store.values.toList()
  }

  override fun save(entity: MoneyTransferSummary) {
    store[entity.moneyTransferId] = entity
  }
}
