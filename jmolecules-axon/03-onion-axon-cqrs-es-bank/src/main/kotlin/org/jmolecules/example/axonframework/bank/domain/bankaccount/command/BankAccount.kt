package org.jmolecules.example.axonframework.bank.domain.bankaccount.command

import org.axonframework.springboot.autoconfig.InfraConfiguration
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.bankaccount.CreateBankAccountCommand
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.MoneyDepositedEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.command.BankAccountCreationVerificationResult.*
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.*
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferCancelledEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferCompletedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferReceivedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferRequestedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.command.ActiveMoneyTransfers
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferNotFound
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason
import org.jmolecules.example.axonframework.infrastructure.configuration.InfrastructureConfiguration

/**
 * Represents bank account.
 */
class BankAccount(
  private val accountId: AccountId,
  private var balanceModel: BankAccountBalance,
  private val activeMoneyTransfers: ActiveMoneyTransfers
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
        is InsufficientBalanceAmountVerificationResult -> throw InsufficientBalance(
          accountId = accountId,
          withdrawAmount = null,
          currentBalance = initialBalance,
          minimumBalance = result.minimumBalance
        )

        is BalanceAmountExceededVerificationResult -> throw MaximumBalanceExceeded(
          accountId = accountId,
          depositAmount = null,
          currentBalance = initialBalance,
          maximumBalance = result.maximumBalance
        )

        ValidBalanceAmountVerificationResult -> BankAccountCreatedEvent(
          accountId = accountId,
          initialBalance = initialBalance
        )
      }

    fun initializeAccount(accountId: AccountId, initialBalance: Balance) =
      BankAccount(
        accountId = accountId,
        balanceModel = BankAccountBalance(
          currentBalance = initialBalance,
          maximumBalance = MAX_BALANCE,
          minimumBalance = MIN_BALANCE
        ),
        activeMoneyTransfers = ActiveMoneyTransfers()
      )
  }

  // ------------------------------------------------------------------

  // Model validation

  fun depositMoney(amount: Amount): MoneyDepositedEvent {
    // ****
    InfrastructureConfiguration //TODO das sollte knallen!!!!!!
    // ****
    if (!balanceModel.canIncrease(amount)) {
      throw MaximumBalanceExceeded(
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

  fun withdrawMoney(amount: Amount): MoneyWithdrawnEvent {
    if (!balanceModel.canDecrease(amount, activeMoneyTransfers.getReservedAmount())) {
      throw InsufficientBalance(
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

  fun requestMoneyTransfer(
    moneyTransferId: MoneyTransferId,
    sourceAccountId: AccountId,
    targetAccountId: AccountId,
    amount: Amount
  ): MoneyTransferRequestedEvent {

    // TODO: validate that the source account id  matches current account id
    // TODO: validate source != target
    if (!balanceModel.canDecrease(amount, activeMoneyTransfers.getReservedAmount())) {
      throw InsufficientBalance(
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
    // TODO: validate that the target account id  matches current account id
    if (!balanceModel.canIncrease(amount)) {
      throw MaximumBalanceExceeded(
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
    rejectionReason: RejectionReason
  ): MoneyTransferCancelledEvent {
    if (!checkMoneyTransfer(moneyTransferId)) {
      throw MoneyTransferNotFound(accountId = sourceAccountId, moneyTransferId = moneyTransferId)
    }
    return MoneyTransferCancelledEvent(
      moneyTransferId = moneyTransferId,
      reason = rejectionReason
    )
  }

  private fun checkMoneyTransfer(moneyTransferId: MoneyTransferId) =
    activeMoneyTransfers.hasMoneyTransfer(moneyTransferId)

  private fun getAmountForMoneyTransfer(moneyTransferId: MoneyTransferId, sourceAccountId: AccountId): Amount {
    return activeMoneyTransfers.getAmountForTransfer(moneyTransferId)
      ?: throw MoneyTransferNotFound(accountId = sourceAccountId, moneyTransferId = moneyTransferId)
  }

  internal fun getCurrentBalance(): Balance = this.balanceModel.currentBalance

  internal fun getActiveMoneyTransfers(): ActiveMoneyTransfers = this.activeMoneyTransfers

  // -----------------------------------------------------------
  // Model modification

  fun increaseBalance(amount: Amount) {
    balanceModel = balanceModel.increase(amount)
  }

  fun decreaseBalance(amount: Amount) {
    balanceModel = balanceModel.decrease(amount)
  }

  fun initializeMoneyTransfer(moneyTransferId: MoneyTransferId, amount: Amount) {
    activeMoneyTransfers.initTransfer(moneyTransferId = moneyTransferId, amount = amount)
  }

  fun acknowledgeMoneyTransferCompletion(moneyTransferId: MoneyTransferId) {
    val amount = activeMoneyTransfers.getAmountForTransfer(moneyTransferId)
      ?: throw MoneyTransferNotFound(
        accountId,
        moneyTransferId
      )
    balanceModel = balanceModel.decrease(amount)
    activeMoneyTransfers.completeTransfer(moneyTransferId)
  }

  fun acknowledgeMoneyTransferCancellation(moneyTransferId: MoneyTransferId) {
    activeMoneyTransfers.cancelTransfer(moneyTransferId)
  }
}

