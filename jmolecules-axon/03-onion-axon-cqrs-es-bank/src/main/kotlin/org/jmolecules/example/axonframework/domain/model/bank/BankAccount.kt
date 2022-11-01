package org.jmolecules.example.axonframework.domain.model.bank

import org.jmolecules.example.axonframework.domain.api.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.domain.api.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.domain.api.exception.MoneyTransferNotFoundException
import org.jmolecules.example.axonframework.domain.model.moneytransfer.MoneyTransfers
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.Balance
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId

/**
 * Represents bank account.
 */
class BankAccount(
    private val accountId: AccountId,
    private val balanceModel: BankAccountBalance,
    private val moneyTransfers: MoneyTransfers
) {
    companion object {
        /**
         * We do not lend money. Never. To anyone.
         */
        private val MIN_BALANCE = Balance(0)

        /**
         * We believe that no one will ever need more money than this. (this rule allows easier testing of failures on transfers).
         */
        private val MAX_BALANCE = Balance(1000)

        fun canCreateBankAccount(initialBalance: Balance) =
            BankAccountBalance.validateInitialBalance(
                balance = initialBalance,
                maximumBalance = MAX_BALANCE,
                minimumBalance = MIN_BALANCE
            )

        fun createBankAccount(initialBalance: Balance, accountId: AccountId) =
            BankAccount(
                accountId = accountId,
                balanceModel = BankAccountBalance(
                    currentBalance = initialBalance,
                    maximumBalance = MAX_BALANCE,
                    minimumBalance = MIN_BALANCE
                ),
                moneyTransfers = MoneyTransfers()
            )
    }


    fun requireIncreaseAllowed(amount: Amount) {
        if (!balanceModel.canIncrease(amount)) {
            throw MaximumBalanceExceededException(
                accountId = accountId,
                currentBalance = balanceModel.currentBalance,
                maximumBalance = balanceModel.maximumBalance,
                depositAmount = amount
            )
        }
    }
    fun requireDecreaseAllowed(amount: Amount) {
        if (!balanceModel.canDecrease(amount, moneyTransfers.getReservedAmount())) {
            throw InsufficientBalanceException(
                accountId = accountId,
                currentBalance = balanceModel.currentBalance,
                withdrawAmount = amount,
                minimumBalance = balanceModel.minimumBalance
            )
        }
    }

    fun checkMoneyTransfer(moneyTransferId: MoneyTransferId) = moneyTransfers.hasMoneyTransfer(moneyTransferId)
    fun getAmountForMoneyTransfer(moneyTransferId: MoneyTransferId): Amount? {
        return moneyTransfers.getAmountForTransfer(moneyTransferId)
    }


    fun depositMoney(amount: Amount) {
        balanceModel.increase(amount)
    }

    fun withdrawMoney(amount: Amount) {
        balanceModel.increase(amount)
    }

    fun initMoneyTransfer(moneyTransferId: MoneyTransferId, amount: Amount) {
        moneyTransfers.initTransfer(moneyTransferId = moneyTransferId, amount = amount)
    }

    fun completeMoneyTransfer(moneyTransferId: MoneyTransferId) {
        val amount = moneyTransfers.getAmountForTransfer(moneyTransferId) ?: throw MoneyTransferNotFoundException(accountId, moneyTransferId)
        balanceModel.decrease(amount)
        moneyTransfers.completeTransfer(moneyTransferId)
    }

    fun receiveMoneyTransfer(amount: Amount) {
        balanceModel.increase(amount)
    }

    fun cancelMoneyTransfer(moneyTransferId: MoneyTransferId) {
        moneyTransfers.cancelTransfer(moneyTransferId)
    }

}

