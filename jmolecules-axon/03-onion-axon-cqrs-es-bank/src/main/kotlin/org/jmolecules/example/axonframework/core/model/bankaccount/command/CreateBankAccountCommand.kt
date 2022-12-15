package org.jmolecules.example.axonframework.core.model.bankaccount.command

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Balance

@Command(namespace = "axon.bank", name = "CreateBankAccountCommand")
data class CreateBankAccountCommand(
  @Association
  val accountId: AccountId,
  val initialBalance: Balance
)
