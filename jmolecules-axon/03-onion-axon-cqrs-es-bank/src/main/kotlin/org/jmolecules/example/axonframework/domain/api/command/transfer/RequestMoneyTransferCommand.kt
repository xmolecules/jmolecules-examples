package org.jmolecules.example.axonframework.domain.api.command.transfer

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId

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
