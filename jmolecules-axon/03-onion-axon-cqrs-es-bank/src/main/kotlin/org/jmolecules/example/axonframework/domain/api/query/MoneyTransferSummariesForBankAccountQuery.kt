package org.jmolecules.example.axonframework.domain.api.query

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf
import org.jmolecules.example.axonframework.domain.api.type.AccountId

class MoneyTransferSummariesForBankAccountQuery(
    val accountId: AccountId
) {
    companion object {
        val RESPONSE_TYPE: ResponseType<List<MoneyTransferSummary>> = multipleInstancesOf(
            MoneyTransferSummary::class.java
        )
    }
}
