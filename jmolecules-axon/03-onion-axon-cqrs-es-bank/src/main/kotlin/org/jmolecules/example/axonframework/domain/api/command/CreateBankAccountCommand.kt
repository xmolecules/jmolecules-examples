package org.jmolecules.example.axonframework.domain.api.command

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Balance

@Command(namespace = "axon.bank", name = "CreateBankAccountCommand")
data class CreateBankAccountCommand(
    @Association
    val accountId: AccountId,
    val initialBalance: Balance
)
