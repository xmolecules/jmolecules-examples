package org.jmolecules.example.axonframework.bank.adapter.out.querymodel.moneytransfer

import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.infrastructure.architecture.Query


@ValueObject
@Query(name = "AllMoneyTransfersQuery", namespace = "axon.bank")
object AllMoneyTransfersQuery {
  override fun equals(other: Any?): Boolean {
    return other is AllMoneyTransfersQuery
  }

  override fun hashCode(): Int {
    return AllMoneyTransfersQuery::class.java.hashCode()
  }
}
