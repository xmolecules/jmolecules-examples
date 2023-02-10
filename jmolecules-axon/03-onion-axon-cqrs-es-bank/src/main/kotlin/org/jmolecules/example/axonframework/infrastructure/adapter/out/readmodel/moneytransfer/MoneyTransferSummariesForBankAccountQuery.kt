package org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.moneytransfer

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary

class MoneyTransferSummariesForBankAccountQuery(
  val accountId: AccountId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<List<MoneyTransferSummary>> = multipleInstancesOf(
      MoneyTransferSummary::class.java
    )
  }
}
