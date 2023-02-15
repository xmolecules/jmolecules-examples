package org.jmolecules.example.axonframework.bank.adapter.out.readmodel.bankaccount

import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes.optionalInstanceOf
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.bank.domain.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.infrastructure.architecture.Query
import java.util.*

@ValueObject
@Query(name = "BankAccountCurrentBalanceQuery", namespace = "axon.bank")
data class BankAccountCurrentBalanceQuery(
  val accountId: AccountId
) {
  companion object {
    val RESPONSE_TYPE: ResponseType<Optional<BankAccountCurrentBalance>> = optionalInstanceOf(
      BankAccountCurrentBalance::class.java
    )
  }
}
