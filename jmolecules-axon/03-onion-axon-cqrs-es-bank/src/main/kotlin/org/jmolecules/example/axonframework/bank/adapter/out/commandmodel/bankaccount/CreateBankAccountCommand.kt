package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.bankaccount

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance

@Command(namespace = "axon.bank", name = "CreateBankAccountCommand")
data class CreateBankAccountCommand(
  @Association
  val accountId: AccountId,
  val initialBalance: Balance
)
