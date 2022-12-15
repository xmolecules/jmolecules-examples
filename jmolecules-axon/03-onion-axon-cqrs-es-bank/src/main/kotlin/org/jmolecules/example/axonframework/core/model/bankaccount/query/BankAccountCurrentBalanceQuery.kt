package org.jmolecules.example.axonframework.core.model.bankaccount.query

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import java.util.*

data class BankAccountCurrentBalanceQuery(
  val accountId: AccountId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<Optional<BankAccountCurrentBalance>> = optionalInstanceOf(
      BankAccountCurrentBalance::class.java
    )
  }
}
