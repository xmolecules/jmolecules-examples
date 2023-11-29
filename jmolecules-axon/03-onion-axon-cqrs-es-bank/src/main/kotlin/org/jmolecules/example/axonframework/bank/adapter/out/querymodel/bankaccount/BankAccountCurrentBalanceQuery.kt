package org.jmolecules.example.axonframework.bank.adapter.out.querymodel.bankaccount

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.CurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.infrastructure.architecture.Query
import java.util.*

@ValueObject
@Query(name = "BankAccountCurrentBalanceQuery", namespace = "axon.bank")
data class BankAccountCurrentBalanceQuery(
  val accountId: AccountId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<Optional<CurrentBalance>> = optionalInstanceOf(
      CurrentBalance::class.java
    )
  }
}
