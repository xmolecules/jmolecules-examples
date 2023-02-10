package org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.moneytransfer

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.port.out.repository.MoneyTransferSummaryRepository
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
    return store.values
      .filter { it.sourceAccountId == accountId || it.targetAccountId == accountId }
      .toList()
  }

  override fun findAll(): List<MoneyTransferSummary> {
    return store.values.toList()
  }

  override fun save(entity: MoneyTransferSummary) {
    store[entity.moneyTransferId] = entity
  }
}
