package org.jmolecules.example.axonframework.bank.adapter.out.readmodel

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.bankaccount.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer.AllMoneyTransfersQuery
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer.MoneyTransferSummariesForBankAccountQuery
import org.jmolecules.example.axonframework.bank.adapter.out.readmodel.moneytransfer.MoneyTransferSummaryByIdQuery
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.*
import org.jmolecules.example.axonframework.infrastructure.configuration.InfrastructureConfiguration.Companion.DEFAULT_OBJECT_MAPPER
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Makes sure the queries and query responses are serializable and de-serializable with Jackson.
 */
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
      Arguments.of(
        MoneyTransferSummaries::class.java,
        MoneyTransferSummaries(
          elements = listOf(
            MoneyTransferSummary(
              moneyTransferId = MoneyTransferId.of("0815"),
              sourceAccountId = AccountId.of("4711"),
              targetAccountId = AccountId.of("4712"),
              amount = Amount.of(100),
              status = MoneyTransferStatus.Succeeded
            ),
            MoneyTransferSummary(
              moneyTransferId = MoneyTransferId.of("0815"),
              sourceAccountId = AccountId.of("4711"),
              targetAccountId = AccountId.of("4712"),
              amount = Amount.of(100),
              status = MoneyTransferStatus.InProgress
            ),
            MoneyTransferSummary(
              moneyTransferId = MoneyTransferId.of("0815"),
              sourceAccountId = AccountId.of("4711"),
              targetAccountId = AccountId.of("4712"),
              amount = Amount.of(100),
              status = MoneyTransferStatus.Rejected(rejectionReason = RejectionReason.of("No money"))
            )
          )
        )
      )
      )
  }

  @ParameterizedTest
  @MethodSource("provideQueries")
  fun <Q : Any> `checks serialization is not changing the query or query result`(type: Class<Q>, query: Q) {
    val serialized = objectMapper.writeValueAsString(query)
    val deserialized: Q = objectMapper.readValue(serialized, type)
    assertThat(deserialized).isEqualTo(query)
  }

}
