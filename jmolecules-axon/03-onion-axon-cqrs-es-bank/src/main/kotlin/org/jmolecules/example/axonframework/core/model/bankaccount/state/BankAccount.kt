package org.jmolecules.example.axonframework.core.model.bankaccount.state

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.bankaccount.state.BankAccountCreationVerificationResult.*
import org.jmolecules.example.axonframework.core.model.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyDepositedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Balance
import org.jmolecules.example.axonframework.core.model.moneytransfer.state.BankAccountMoneyTransfers
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.Reason
import org.jmolecules.example.axonframework.core.model.moneytransfer.exception.MoneyTransferNotFoundException
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferCancelledEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferCompletedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferReceivedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferRequestedEvent

/**
 * Represents bank account.
 */
class BankAccount(
  private val accountId: AccountId,
  private val balanceModel: BankAccountBalance,
  private val moneyTransfers: BankAccountMoneyTransfers
) {
  companion object {
    /**
     * We do not lend money. Never. To anyone.
     */
    private val MIN_BALANCE = Balance.of(0)

    /**
     * We believe that no one will ever need more money than this. (this rule allows easier testing of failures on transfers).
     */
    private val MAX_BALANCE = Balance.of(1000)

    fun createAccount(accountId: AccountId, initialBalance: Balance) =
      when (val result = BankAccountBalance.validateInitialBalance(
        balance = initialBalance,
        maximumBalance = MAX_BALANCE,
        minimumBalance = MIN_BALANCE
      )) {
        is InsufficientBalanceAmountVerificationResult -> throw InsufficientBalanceException(
          accountId = accountId,
          withdrawAmount = null,
          currentBalance = initialBalance,
          minimumBalance = result.minimumBalance
        )

        is BalanceAmountExceededVerificationResult -> throw MaximumBalanceExceededException(
          accountId = accountId,
          depositAmount = null,
          currentBalance = initialBalance,
          maximumBalance = result.maximumBalance
        )

        ValidBalanceAmountVerificationResult ->
          BankAccountCreatedEvent(
            accountId = accountId,
            initialBalance = initialBalance
          )
      }

    fun initializeAccount(initialBalance: Balance, accountId: AccountId) =
      BankAccount(
        accountId = accountId,
        balanceModel = BankAccountBalance(
          currentBalance = initialBalance,
          maximumBalance = MAX_BALANCE,
          minimumBalance = MIN_BALANCE
        ),
        moneyTransfers = BankAccountMoneyTransfers()
      )
  }

  // ------------------------------------------------------------------

  // Model validation

  fun depositMoney(amount: Amount): MoneyDepositedEvent {
    if (!balanceModel.canIncrease(amount)) {
      throw MaximumBalanceExceededException(
        accountId = accountId,
        currentBalance = balanceModel.currentBalance,
        maximumBalance = balanceModel.maximumBalance,
        depositAmount = amount
      )
    }
    return MoneyDepositedEvent(
      accountId = accountId,
      amount = amount
    )
  }

  fun requestMoneyTransfer(
    moneyTransferId: MoneyTransferId,
    sourceAccountId: AccountId,
    targetAccountId: AccountId,
    amount: Amount
  ): MoneyTransferRequestedEvent {
    if (!balanceModel.canDecrease(amount, moneyTransfers.getReservedAmount())) {
      throw InsufficientBalanceException(
        accountId = accountId,
        currentBalance = balanceModel.currentBalance,
        withdrawAmount = amount,
        minimumBalance = balanceModel.minimumBalance
      )
    }
    return MoneyTransferRequestedEvent(
      moneyTransferId = moneyTransferId,
      sourceAccountId = sourceAccountId,
      targetAccountId = targetAccountId,
      amount = amount
    )
  }

  fun receiveMoneyTransfer(
    moneyTransferId: MoneyTransferId,
    targetAccountId: AccountId,
    amount: Amount
  ): MoneyTransferReceivedEvent {
    if (!balanceModel.canIncrease(amount)) {
      throw MaximumBalanceExceededException(
        accountId = accountId,
        currentBalance = balanceModel.currentBalance,
        maximumBalance = balanceModel.maximumBalance,
        depositAmount = amount
      )
    }
    return MoneyTransferReceivedEvent(
      moneyTransferId = moneyTransferId,
      targetAccountId = targetAccountId,
      amount = amount
    )
  }

  fun withdrawMoney(amount: Amount): MoneyWithdrawnEvent {
    if (!balanceModel.canDecrease(amount, moneyTransfers.getReservedAmount())) {
      throw InsufficientBalanceException(
        accountId = accountId,
        currentBalance = balanceModel.currentBalance,
        withdrawAmount = amount,
        minimumBalance = balanceModel.minimumBalance
      )
    }
    return MoneyWithdrawnEvent(
      accountId = accountId,
      amount = amount
    )
  }

  fun completeMoneyTransfer(moneyTransferId: MoneyTransferId, sourceAccountId: AccountId): MoneyTransferCompletedEvent {
    val amount = getAmountForMoneyTransfer(moneyTransferId, sourceAccountId)
    return MoneyTransferCompletedEvent(
      moneyTransferId = moneyTransferId,
      sourceAccountId = sourceAccountId,
      amount = amount
    )
  }

  fun cancelMoneyTransfer(
    moneyTransferId: MoneyTransferId,
    sourceAccountId: AccountId,
    reason: Reason
  ): MoneyTransferCancelledEvent {
    if (!checkMoneyTransfer(moneyTransferId)) {
      throw MoneyTransferNotFoundException(accountId = sourceAccountId, moneyTransferId = moneyTransferId)
    }
    return MoneyTransferCancelledEvent(
      moneyTransferId = moneyTransferId,
      reason = reason
    )
  }

  private fun checkMoneyTransfer(moneyTransferId: MoneyTransferId) = moneyTransfers.hasMoneyTransfer(moneyTransferId)

  private fun getAmountForMoneyTransfer(moneyTransferId: MoneyTransferId, sourceAccountId: AccountId): Amount {
    return moneyTransfers.getAmountForTransfer(moneyTransferId)
      ?: throw MoneyTransferNotFoundException(accountId = sourceAccountId, moneyTransferId = moneyTransferId)
  }

  // -----------------------------------------------------------
  // Model modification

  fun increaseAmount(amount: Amount) {
    balanceModel.increase(amount)
  }

  fun decreaseAmount(amount: Amount) {
    balanceModel.decrease(amount)
  }

  fun initializeMoneyTransfer(moneyTransferId: MoneyTransferId, amount: Amount) {
    moneyTransfers.initTransfer(moneyTransferId = moneyTransferId, amount = amount)
  }

  fun acknowledgeMoneyTransferCompletion(moneyTransferId: MoneyTransferId) {
    val amount = moneyTransfers.getAmountForTransfer(moneyTransferId)
      ?: throw MoneyTransferNotFoundException(
        accountId,
        moneyTransferId
      )
    balanceModel.decrease(amount)
    moneyTransfers.completeTransfer(moneyTransferId)
  }

  fun acknowledgeMoneyTransferCancellation(moneyTransferId: MoneyTransferId) {
    moneyTransfers.cancelTransfer(moneyTransferId)
  }
}

