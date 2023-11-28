package org.jmolecules.example.axonframework.bank.adapter.out.querymodel.moneytransfer

import org.axonframework.queryhandling.QueryHandler
import org.jmolecules.architecture.cqrs.QueryModel
import org.jmolecules.example.axonframework.bank.application.port.out.repository.MoneyTransferSummaryRepository
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.query.BankAccountMoneyTransfer
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferStatus
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummaries
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummary
import java.util.*

/**
 * Query domain for the money transfers.
 */
@QueryModel
class MoneyTransferSummaryProjection(
  private val repository: MoneyTransferSummaryRepository
) {

  /**
   * Finds money transfer by id.
   * @param query query containing the id.
   * @return money transfer or an empty option.
   */
  @QueryHandler
  fun query(query: MoneyTransferSummaryByIdQuery): Optional<MoneyTransferSummary> {
    return repository.findById(query.moneyTransferId).map { it.toDomain() }
  }

  /**
   * Finds all money transfers involving given account.
   * @param query query containing the account id.
   * @return list of money transfers.
   */
  @QueryHandler
  fun query(query: MoneyTransferSummariesForBankAccountQuery): MoneyTransferSummaries {
    return MoneyTransferSummaries(elements = repository.findByAccountId(query.accountId).map { it.toDomain() })
  }

  /**
   * Finds all money transfers.
   */
  @QueryHandler
  fun query(query: AllMoneyTransfersQuery): List<MoneyTransferSummary> {
    return repository.findAll().map { it.toDomain() }
  }

  fun BankAccountMoneyTransfer.toDomain() = MoneyTransferSummary(
    moneyTransferId = this.moneyTransferId,
    sourceAccountId = this.sourceAccountId,
    targetAccountId = this.targetAccountId,
    amount = this.amount,
    status = MoneyTransferStatus.of(this.success, this.errorMessage),
  )
}
