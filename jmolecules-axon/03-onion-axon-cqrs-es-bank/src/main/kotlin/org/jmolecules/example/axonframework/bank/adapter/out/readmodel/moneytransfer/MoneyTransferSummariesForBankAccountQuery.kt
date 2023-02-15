package org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf
import org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.MoneyTransferSummaries
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.infrastructure.architecture.Query

@ValueObject
@Query(name = "MoneyTransferSummariesForBankAccountQuery", namespace = "axon.bank")
data class MoneyTransferSummariesForBankAccountQuery(
  val accountId: AccountId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<MoneyTransferSummaries> = instanceOf(
      MoneyTransferSummaries::class.java
    )
  }
}
