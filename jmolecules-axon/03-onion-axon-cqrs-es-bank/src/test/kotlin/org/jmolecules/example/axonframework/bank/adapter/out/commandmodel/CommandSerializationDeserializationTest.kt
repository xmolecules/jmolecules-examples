package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.DepositMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.WithdrawMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.bankaccount.CreateBankAccountCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CancelMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CompleteMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.ReceiveMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.RequestMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.Reason
import org.jmolecules.example.axonframework.infrastructure.InfrastructureConfiguration.Companion.DEFAULT_OBJECT_MAPPER
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class CommandSerializationDeserializationTest {

  private val objectMapper: ObjectMapper = DEFAULT_OBJECT_MAPPER

  companion object {
    @JvmStatic
    fun provideCommands() = Stream.of(
      Arguments.of(
        CreateBankAccountCommand::class.java,
        CreateBankAccountCommand(
          accountId = AccountId.of("4711"),
          initialBalance = Balance.of(100)
        )
      ),
      Arguments.of(
        DepositMoneyCommand::class.java,
        DepositMoneyCommand(
          accountId = AccountId.of("4711"),
          amount = Amount.of(100)
        )
      ),
      Arguments.of(
        WithdrawMoneyCommand::class.java,
        WithdrawMoneyCommand(
          accountId = AccountId.of("4711"),
          amount = Amount.of(100)
        )
      ),
      Arguments.of(
        CancelMoneyTransferCommand::class.java,
        CancelMoneyTransferCommand(
          moneyTransferId = MoneyTransferId.of("0815"),
          sourceAccountId = AccountId.of("4711"),
          targetAccountId = AccountId.of("4712"),
          reason = Reason.of("no money")
        )
      ),
      Arguments.of(
        CompleteMoneyTransferCommand::class.java,
        CompleteMoneyTransferCommand(
          moneyTransferId = MoneyTransferId.of("0815"),
          sourceAccountId = AccountId.of("4711")
        )
      ),
      Arguments.of(
        ReceiveMoneyTransferCommand::class.java,
        ReceiveMoneyTransferCommand(
          moneyTransferId = MoneyTransferId.of("0815"),
          targetAccountId = AccountId.of("4712"),
          amount = Amount.of(100)
        )
      ),
      Arguments.of(
        RequestMoneyTransferCommand::class.java,
        RequestMoneyTransferCommand(
          moneyTransferId = MoneyTransferId.of("0815"),
          sourceAccountId = AccountId.of("4711"),
          targetAccountId = AccountId.of("4712"),
          amount = Amount.of(100)
        )
      ),
      )
  }

  @ParameterizedTest
  @MethodSource("provideCommands")
  fun <Q : Any> `checks serialization is not changing the command`(type: Class<Q>, command: Q) {
    val serialized = objectMapper.writeValueAsString(command)
    val deserialized: Q = objectMapper.readValue(serialized, type)
    assertThat(deserialized).isEqualTo(command)
  }

}