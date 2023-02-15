package org.jmolecules.example.axonframework.bank.application.port.out.repository

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import java.util.Optional

/**
 * Repository for money transfers.
 */
interface MoneyTransferSummaryRepository {
  fun findById(id: MoneyTransferId): Optional<MoneyTransferSummary>
  fun findByAccountId(accountId: AccountId): List<MoneyTransferSummary>
  fun findAll(): List<MoneyTransferSummary>
  fun save(entity: MoneyTransferSummary)
}
