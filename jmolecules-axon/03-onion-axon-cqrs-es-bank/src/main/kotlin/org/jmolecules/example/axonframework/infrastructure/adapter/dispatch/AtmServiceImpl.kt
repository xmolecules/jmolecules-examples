package org.jmolecules.example.axonframework.infrastructure.adapter.dispatch

import org.axonframework.commandhandling.gateway.CommandGateway
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount
import org.jmolecules.example.axonframework.core.model.bankaccount.command.DepositMoneyCommand
import org.jmolecules.example.axonframework.core.model.bankaccount.command.WithdrawMoneyCommand
import org.jmolecules.example.axonframework.core.usecase.atm.AtmService
import org.springframework.stereotype.Component

/**
 * Implementation of the ATM Service using Axon Command bus to dispatch commands.
 */
@Component
class AtmServiceImpl(
  private val commandGateway: CommandGateway
) : AtmService {
  override fun withdrawMoney(accountId: AccountId, amount: Amount) {
    commandGateway.sendAndWait<Void>(WithdrawMoneyCommand(accountId, amount))
  }

  override fun depositMoney(accountId: AccountId, amount: Amount) {
    commandGateway.sendAndWait<Void>(DepositMoneyCommand(accountId, amount))
  }
}
