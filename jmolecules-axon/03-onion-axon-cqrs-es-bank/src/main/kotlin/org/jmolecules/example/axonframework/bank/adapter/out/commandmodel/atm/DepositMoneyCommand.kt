package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount

/**
 * Deposit money command.
 */
@Command(namespace = "axon.bank", name = "DepositMoneyCommand")
data class DepositMoneyCommand(
  @Association
  val accountId: AccountId,
  val amount: Amount
)
