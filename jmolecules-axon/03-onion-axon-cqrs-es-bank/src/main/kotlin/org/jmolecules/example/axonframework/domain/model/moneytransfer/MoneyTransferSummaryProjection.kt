package org.jmolecules.example.axonframework.domain.model.moneytransfer

import org.jmolecules.example.axonframework.domain.api.query.MoneyTransferSummaryByIdQuery
import org.jmolecules.example.axonframework.domain.api.query.MoneyTransferSummary
import org.jmolecules.example.axonframework.domain.api.query.MoneyTransferSummariesForBankAccountQuery
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

