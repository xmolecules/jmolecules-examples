package org.jmolecules.example.axonframework.core.model.bankaccount.command

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount

/**
 * Withdraw money command.
 */
@Command(namespace = "axon.bank", name = "WithdrawMoneyCommand")
data class WithdrawMoneyCommand(
  @Association
  val accountId: AccountId,
  val amount: Amount
)
