package org.jmolecules.example.axonframework.core.model.moneytransfer.query

import java.util.*

/**
 * Query model for the money transfers.
 */
interface MoneyTransferSummaryProjection {

  /**
   * Finds money transfer by id.
   * @param query query containing the id.
   * @return money transfer or an empty option.
   */
  fun query(query: MoneyTransferSummaryByIdQuery): Optional<MoneyTransferSummary>

  /**
   * Finds all money transfers involving given account.
   * @param query query containing the account id.
   * @return list of money transfers.
   */
  fun query(query: MoneyTransferSummariesForBankAccountQuery): List<MoneyTransferSummary>
}

