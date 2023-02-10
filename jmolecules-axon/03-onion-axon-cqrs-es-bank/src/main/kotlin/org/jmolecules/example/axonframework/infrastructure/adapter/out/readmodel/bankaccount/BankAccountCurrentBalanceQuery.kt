package org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.bankaccount

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import java.util.*

@ValueObject
data class BankAccountCurrentBalanceQuery(
  val accountId: AccountId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<Optional<BankAccountCurrentBalance>> = optionalInstanceOf(
      BankAccountCurrentBalance::class.java
    )
  }
}
