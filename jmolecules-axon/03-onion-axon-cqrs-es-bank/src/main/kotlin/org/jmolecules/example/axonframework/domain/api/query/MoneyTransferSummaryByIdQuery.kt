package org.jmolecules.example.axonframework.domain.api.query

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
import java.util.*

data class MoneyTransferSummaryByIdQuery(val moneyTransferId: MoneyTransferId) {

    companion object {
        val RESPONSE_TYPE: ResponseType<Optional<MoneyTransferSummary>> = optionalInstanceOf(
            MoneyTransferSummary::class.java
        )
    }
}
