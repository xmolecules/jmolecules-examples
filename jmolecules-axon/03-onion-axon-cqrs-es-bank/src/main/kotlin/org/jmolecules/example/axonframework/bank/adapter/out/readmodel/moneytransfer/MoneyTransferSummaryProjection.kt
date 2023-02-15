package org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer

import org.axonframework.queryhandling.QueryHandler
import org.jmolecules.architecture.cqrs.annotation.QueryModel
import org.jmolecules.example.axonframework.bank.application.port.out.repository.MoneyTransferSummaryRepository
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.MoneyTransferSummaries
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.MoneyTransferSummary
import java.util.Optional

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
    return repository.findById(query.moneyTransferId)
  }

  /**
   * Finds all money transfers involving given account.
   * @param query query containing the account id.
   * @return list of money transfers.
   */
  @QueryHandler
  fun query(query: MoneyTransferSummariesForBankAccountQuery): MoneyTransferSummaries {
    return MoneyTransferSummaries(elements = repository.findByAccountId(query.accountId))
  }

  /**
   * Finds all money transfers.
   */
  @QueryHandler
  fun query(query: AllMoneyTransfersQuery): List<MoneyTransferSummary> {
    return repository.findAll()
  }

}
