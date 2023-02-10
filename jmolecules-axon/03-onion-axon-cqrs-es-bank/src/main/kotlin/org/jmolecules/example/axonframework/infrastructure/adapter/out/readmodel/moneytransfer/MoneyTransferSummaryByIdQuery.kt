package org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.moneytransfer

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import java.util.*

@ValueObject
data class MoneyTransferSummaryByIdQuery(
  val moneyTransferId: MoneyTransferId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<Optional<MoneyTransferSummary>> = optionalInstanceOf(
      MoneyTransferSummary::class.java
    )
  }
}
