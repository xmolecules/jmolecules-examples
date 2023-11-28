package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer

import org.jmolecules.architecture.cqrs.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId

/**
 * Received by the source account.
 * Reserves amount for transfer and starts saga.
 */
@Command(namespace = "axon.bank", name = "RequestMoneyTransferCommand")
data class RequestMoneyTransferCommand(
  @Association
  val sourceAccountId: AccountId,
  val targetAccountId: AccountId,
  val amount: Amount,
  val moneyTransferId: MoneyTransferId
)
