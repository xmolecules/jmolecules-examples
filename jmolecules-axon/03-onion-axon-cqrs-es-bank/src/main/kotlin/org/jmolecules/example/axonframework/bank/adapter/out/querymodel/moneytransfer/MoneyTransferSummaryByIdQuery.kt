package org.jmolecules.example.axonframework.bank.adapter.out.querymodel.moneytransfer

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummary
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.infrastructure.architecture.Query
import java.util.*

@ValueObject
@Query(name = "MoneyTransferSummaryByIdQuery", namespace = "axon.bank")
data class MoneyTransferSummaryByIdQuery(
  val moneyTransferId: MoneyTransferId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<Optional<MoneyTransferSummary>> = optionalInstanceOf(
      MoneyTransferSummary::class.java
    )
  }
}
