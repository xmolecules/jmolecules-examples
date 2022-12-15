package org.jmolecules.example.axonframework.core.model.bankaccount.command

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount

/**
 * Deposit money command.
 */
@Command(namespace = "axon.bank", name = "DepositMoneyCommand")
data class DepositMoneyCommand(
  @Association
  val accountId: AccountId,
  val amount: Amount
)
