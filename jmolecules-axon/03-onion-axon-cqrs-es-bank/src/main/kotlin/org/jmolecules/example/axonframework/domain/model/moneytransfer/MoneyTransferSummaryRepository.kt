package org.jmolecules.example.axonframework.domain.model.moneytransfer

import org.jmolecules.example.axonframework.domain.api.query.MoneyTransferSummary
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
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
