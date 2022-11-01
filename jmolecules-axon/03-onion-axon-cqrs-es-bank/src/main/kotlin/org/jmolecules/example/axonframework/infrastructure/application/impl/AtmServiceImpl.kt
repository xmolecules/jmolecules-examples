package org.jmolecules.example.axonframework.infrastructure.application.impl

import org.axonframework.commandhandling.gateway.CommandGateway
import org.jmolecules.example.axonframework.application.atm.AtmService
import org.jmolecules.example.axonframework.domain.api.command.atm.DepositMoneyCommand
import org.jmolecules.example.axonframework.domain.api.command.atm.WithdrawMoneyCommand
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
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
