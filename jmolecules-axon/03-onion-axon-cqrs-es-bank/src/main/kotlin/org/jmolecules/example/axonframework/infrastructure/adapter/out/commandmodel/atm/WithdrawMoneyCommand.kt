package org.jmolecules.example.axonframework.infrastructure.adapter.out.commandmodel.atm

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount

/**
 * Withdraw money command.
 */
@Command(namespace = "axon.bank", name = "WithdrawMoneyCommand")
data class WithdrawMoneyCommand(
  @Association
  val accountId: AccountId,
  val amount: Amount
)
