package org.jmolecules.example.axonframework.bank.adapter.out.readmodel

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.DepositMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.WithdrawMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.bankaccount.CreateBankAccountCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CancelMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CompleteMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.ReceiveMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.RequestMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.bankaccount.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer.AllMoneyTransfersQuery
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer.MoneyTransferSummariesForBankAccountQuery
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer.MoneyTransferSummaryByIdQuery
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason
import org.jmolecules.example.axonframework.infrastructure.InfrastructureConfiguration.Companion.DEFAULT_OBJECT_MAPPER
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class QuerySerializationDeserializationTest {

  private val objectMapper: ObjectMapper = DEFAULT_OBJECT_MAPPER

  companion object {
    @JvmStatic
    fun provideQueries() = Stream.of(
      Arguments.of(
        BankAccountCurrentBalanceQuery::class.java,
        BankAccountCurrentBalanceQuery(
          accountId = AccountId.of("4711")
        )
      ),
      Arguments.of(
        MoneyTransferSummariesForBankAccountQuery::class.java,
        MoneyTransferSummariesForBankAccountQuery(
          accountId = AccountId.of("4711")
        )
      ),
      Arguments.of(
        MoneyTransferSummaryByIdQuery::class.java,
        MoneyTransferSummaryByIdQuery(
          moneyTransferId = MoneyTransferId.of("0815")
        )
      ),
      Arguments.of(
        AllMoneyTransfersQuery::class.java,
        AllMoneyTransfersQuery
      ),
      )
  }

  @ParameterizedTest
  @MethodSource("provideQueries")
  fun <Q : Any> `checks serialization is not changing the query`(type: Class<Q>, query: Q) {
    val serialized = objectMapper.writeValueAsString(query)
    val deserialized: Q = objectMapper.readValue(serialized, type)
    assertThat(deserialized).isEqualTo(query)
  }

}
