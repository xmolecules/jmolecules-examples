package org.jmolecules.example.axonframework.core.model.moneytransfer.query

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId
import java.util.*

data class MoneyTransferSummaryByIdQuery(val moneyTransferId: MoneyTransferId) {

  companion object {
    val RESPONSE_TYPE: ResponseType<Optional<MoneyTransferSummary>> = optionalInstanceOf(
      MoneyTransferSummary::class.java
    )
  }
}
