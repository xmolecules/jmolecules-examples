package org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.bankaccount

import mu.KLogging
import org.axonframework.queryhandling.QueryHandler
import org.jmolecules.architecture.cqrs.annotation.QueryModel
import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.port.out.repository.BankAccountCurrentBalanceRepository
import org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.bankaccount.BankAccountCurrentBalanceQuery
import java.util.*

@QueryModel
class BankAccountCurrentBalanceProjection(
  private val repository: BankAccountCurrentBalanceRepository
) {

  companion object: KLogging()

  @QueryHandler
  fun query(query: BankAccountCurrentBalanceQuery): Optional<BankAccountCurrentBalance> {
    logger.info { "Querying account ${query.accountId}" }
    return repository.findById(query.accountId)
  }
}
