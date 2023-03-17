package org.jmolecules.example.axonframework.bank.domain.bankaccount.state

import org.assertj.core.api.Assertions.assertThat
import org.jmolecules.example.axonframework.bank.domain.bankaccount.command.BankAccount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.*
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferNotFound
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BankAccountTest {

  private val accountId = AccountId.of("4711")
  private val otherAccountId = AccountId.of("4712")
  private val tooLowInitialBalance = Balance.of(-10)
  private val validInitialBalance = Balance.of(17)
  private val tooHighInitialBalance = Balance.of(1234)
  private val moneyTransferId = MoneyTransferId.of("0815")

  @Test
  fun `create bank account with valid balance`() {
    val event = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    )
    assertThat(event).isEqualTo(BankAccountCreatedEvent(accountId, validInitialBalance))
  }

  @Test
  fun `fail to create bank account with insufficient balance`() {
    val ex = assertThrows<InsufficientBalance> {
      BankAccount.createAccount(
        accountId = accountId,
        initialBalance = tooLowInitialBalance
      )
    }
    assertThat(ex.accountId).isEqualTo(accountId)
    assertThat(ex.currentBalance).isEqualTo(tooLowInitialBalance)
  }

  @Test
  fun `fail to create bank account with too high balance`() {
    val ex = assertThrows<MaximumBalanceExceeded> {
      BankAccount.createAccount(
        accountId = accountId,
        initialBalance = tooHighInitialBalance
      )
    }
    assertThat(ex.accountId).isEqualTo(accountId)
    assertThat(ex.currentBalance).isEqualTo(tooHighInitialBalance)
  }

  @Test
  fun `deposit money`() {
    val amount = Amount.of(500)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val event = bankAccount.depositMoney(amount)
    assertThat(event.accountId).isEqualTo(accountId)
    assertThat(event.amount).isEqualTo(amount)

    bankAccount.increaseBalance(event.amount)
    assertThat(bankAccount.getCurrentBalance()).isEqualTo(validInitialBalance + amount)
  }

  @Test
  fun `fail to deposit money`() {
    val amount = Amount.of(1000)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val ex = assertThrows<MaximumBalanceExceeded> {
      bankAccount.depositMoney(amount)
    }
    assertThat(ex.accountId).isEqualTo(accountId)
    assertThat(ex.currentBalance).isEqualTo(validInitialBalance)
  }

  @Test
  fun `withdraw money`() {
    val amount = Amount.of(16)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val event = bankAccount.withdrawMoney(amount)
    assertThat(event.accountId).isEqualTo(accountId)
    assertThat(event.amount).isEqualTo(amount)

    bankAccount.decreaseBalance(event.amount)
    assertThat(bankAccount.getCurrentBalance()).isEqualTo(validInitialBalance - amount)
  }

  @Test
  fun `fail to withdraw money`() {
    val amount = Amount.of(23)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val ex = assertThrows<InsufficientBalance> {
      bankAccount.withdrawMoney(amount)
    }
    assertThat(ex.accountId).isEqualTo(accountId)
    assertThat(ex.currentBalance).isEqualTo(validInitialBalance)
  }

  @Test
  fun `request money transfer`() {
    val amount = Amount.of(11)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val event = bankAccount.requestMoneyTransfer(
      moneyTransferId = moneyTransferId,
      sourceAccountId = accountId,
      targetAccountId = otherAccountId,
      amount = amount
    )

    assertThat(event.moneyTransferId).isEqualTo(moneyTransferId)
    assertThat(event.sourceAccountId).isEqualTo(accountId)
    assertThat(event.targetAccountId).isEqualTo(otherAccountId)
    assertThat(event.amount).isEqualTo(amount)

    bankAccount.initializeMoneyTransfer(event.moneyTransferId, event.amount)

    assertThat(bankAccount.getActiveMoneyTransfers().hasMoneyTransfer(event.moneyTransferId)).isTrue
    assertThat(bankAccount.getActiveMoneyTransfers().getAmountForTransfer(event.moneyTransferId)).isNotNull
    assertThat(bankAccount.getActiveMoneyTransfers().getAmountForTransfer(event.moneyTransferId)!!).isEqualTo(amount)
  }

  @Test
  fun `fail to request money transfer`() {
    val amount = Amount.of(23)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val ex = assertThrows<InsufficientBalance> {
      bankAccount.requestMoneyTransfer(
        moneyTransferId = moneyTransferId,
        sourceAccountId = accountId,
        targetAccountId = otherAccountId,
        amount = amount
      )
    }
    assertThat(ex.accountId).isEqualTo(accountId)
    assertThat(ex.currentBalance).isEqualTo(validInitialBalance)

  }

  @Test
  fun `receive money transfer`() {
    val amount = Amount.of(11)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val event = bankAccount.receiveMoneyTransfer(
      moneyTransferId = moneyTransferId,
      targetAccountId = otherAccountId,
      amount = amount
    )

    assertThat(event.moneyTransferId).isEqualTo(moneyTransferId)
    assertThat(event.targetAccountId).isEqualTo(otherAccountId)
    assertThat(event.amount).isEqualTo(amount)

  }

  @Test
  fun `fail to receive money transfer`() {
    val amount = Amount.of(1000)
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val ex = assertThrows<MaximumBalanceExceeded> {
      bankAccount.receiveMoneyTransfer(
        moneyTransferId = moneyTransferId,
        targetAccountId = otherAccountId,
        amount = amount
      )
    }

    assertThat(ex.accountId).isEqualTo(accountId)
    assertThat(ex.currentBalance).isEqualTo(validInitialBalance)
  }

  @Test
  fun `request and complete money transfer`() {
    val amount = Amount.of(11)
    val other = MoneyTransferId.of("other")
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val event = bankAccount.requestMoneyTransfer(
      moneyTransferId = moneyTransferId,
      sourceAccountId = accountId,
      targetAccountId = otherAccountId,
      amount = amount
    )

    bankAccount.initializeMoneyTransfer(event.moneyTransferId, event.amount)
    assertThat(bankAccount.getActiveMoneyTransfers().hasMoneyTransfer(event.moneyTransferId)).isTrue

    val ex = assertThrows<MoneyTransferNotFound> {
      bankAccount.completeMoneyTransfer(
        moneyTransferId = other,
        sourceAccountId = event.sourceAccountId
      )
    }
    assertThat(ex.moneyTransferId).isEqualTo(other)
    assertThat(ex.accountId).isEqualTo(accountId)

    val completion = bankAccount.completeMoneyTransfer(
      moneyTransferId = event.moneyTransferId,
      sourceAccountId = event.sourceAccountId
    )
    assertThat(completion.moneyTransferId).isEqualTo(event.moneyTransferId)
    assertThat(completion.sourceAccountId).isEqualTo(event.sourceAccountId)

    bankAccount.acknowledgeMoneyTransferCompletion(event.moneyTransferId)

    // no active transfer anymore
    assertThat(bankAccount.getActiveMoneyTransfers().hasMoneyTransfer(event.moneyTransferId)).isFalse
    assertThat(bankAccount.getActiveMoneyTransfers().getAmountForTransfer(event.moneyTransferId)).isNull()
    // amount decreased
    assertThat(bankAccount.getCurrentBalance()).isEqualTo(validInitialBalance - amount)
  }

  @Test
  fun `request and fail to complete money transfer`() {
    val amount = Amount.of(11)
    val unknown = MoneyTransferId.of("unknown")
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val event = bankAccount.requestMoneyTransfer(
      moneyTransferId = moneyTransferId,
      sourceAccountId = accountId,
      targetAccountId = otherAccountId,
      amount = amount
    )

    bankAccount.initializeMoneyTransfer(event.moneyTransferId, event.amount)
    assertThat(bankAccount.getActiveMoneyTransfers().hasMoneyTransfer(event.moneyTransferId)).isTrue
    val ex = assertThrows<MoneyTransferNotFound> {
      bankAccount.acknowledgeMoneyTransferCompletion(unknown)
    }
    assertThat(ex.accountId).isEqualTo(accountId)
    assertThat(ex.moneyTransferId).isEqualTo(unknown)
  }

  @Test
  fun `request and cancel money transfer`() {
    val amount = Amount.of(11)
    val reason = RejectionReason.of("reason")
    val otherTransfer = MoneyTransferId.of("other")
    val bankAccount = BankAccount.createAccount(
      accountId = accountId,
      initialBalance = validInitialBalance
    ).let { BankAccount.initializeAccount(accountId = it.accountId, initialBalance = it.initialBalance) }

    val event = bankAccount.requestMoneyTransfer(
      moneyTransferId = moneyTransferId,
      sourceAccountId = accountId,
      targetAccountId = otherAccountId,
      amount = amount
    )

    bankAccount.initializeMoneyTransfer(event.moneyTransferId, event.amount)
    assertThat(bankAccount.getActiveMoneyTransfers().hasMoneyTransfer(event.moneyTransferId)).isTrue

    val ex = assertThrows<MoneyTransferNotFound> {
      bankAccount.cancelMoneyTransfer(
        moneyTransferId = otherTransfer,
        sourceAccountId = accountId,
        rejectionReason = reason
      )
    }

    assertThat(ex.moneyTransferId).isEqualTo(otherTransfer)
    assertThat(ex.accountId).isEqualTo(accountId)

    val cancellation = bankAccount.cancelMoneyTransfer(
      moneyTransferId = event.moneyTransferId,
      sourceAccountId = accountId,
      rejectionReason = reason
    )

    assertThat(cancellation.moneyTransferId).isEqualTo(event.moneyTransferId)
    assertThat(cancellation.reason).isEqualTo(reason)

    bankAccount.acknowledgeMoneyTransferCancellation(event.moneyTransferId)

    // no active transfer anymore
    assertThat(bankAccount.getActiveMoneyTransfers().hasMoneyTransfer(event.moneyTransferId)).isFalse
    assertThat(bankAccount.getActiveMoneyTransfers().getAmountForTransfer(event.moneyTransferId)).isNull()
    // amount back to initial
    assertThat(bankAccount.getCurrentBalance()).isEqualTo(validInitialBalance)
  }

}
