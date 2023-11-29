package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm

import org.jmolecules.architecture.cqrs.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount

/**
 * Withdraw money command.
 */
@Command(namespace = "axon.bank", name = "WithdrawMoneyCommand")
data class WithdrawMoneyCommand(
  @Association
  val accountId: AccountId,
  val amount: Amount
)
