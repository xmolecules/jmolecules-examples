package org.jmolecules.example.axonframework.core.port.out.repository

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
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
