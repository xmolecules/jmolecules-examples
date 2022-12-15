package org.jmolecules.example.axonframework.core.port.storage

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummary
import java.util.*

/**
 * Repository for money transfers.
 */
interface MoneyTransferSummaryRepository {

  fun findById(id: MoneyTransferId): Optional<MoneyTransferSummary>
  fun findByAccountId(accountId: AccountId): List<MoneyTransferSummary>
  fun findAll(): List<MoneyTransferSummary>
  fun save(entity: MoneyTransferSummary)
}
