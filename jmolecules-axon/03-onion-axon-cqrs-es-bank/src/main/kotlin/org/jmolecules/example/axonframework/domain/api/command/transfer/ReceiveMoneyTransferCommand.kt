package org.jmolecules.example.axonframework.domain.api.command.transfer

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId

/**
 * Invoked by the saga, recipient is target account.
 */
@Command(namespace = "axon.bank", name = "ReceiveMoneyTransferCommand")
data class ReceiveMoneyTransferCommand(
    @Association
    val targetAccountId: AccountId,
    val moneyTransferId: MoneyTransferId,
    val amount: Amount
)
