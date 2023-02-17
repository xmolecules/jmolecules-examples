package org.jmolecules.example.axonframework.bank.domain.moneytransfer.type

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Status of money transfer.
 */
@ValueObject
sealed class MoneyTransferStatus {

  companion object {
    fun of(success: Boolean?, rejectionReason: RejectionReason?): MoneyTransferStatus =
      when {
        success == null -> InProgress
        success == true -> Succeeded
        success == false && rejectionReason != null -> Rejected(rejectionReason)
        else -> throw IllegalArgumentException("Could not construct money transfer from $success and $rejectionReason")
      }
  }

  /**
   * Money transfer in progress.
   */
  object InProgress : MoneyTransferStatus() {
    override fun equals(other: Any?): Boolean = other is InProgress
  }

  /**
   * Successful money transfer.
   */
  object Succeeded : MoneyTransferStatus() {
    override fun equals(other: Any?): Boolean = other is Succeeded
  }

  /**
   * Rejected money transfer.
   */
  data class Rejected(val rejectionReason: RejectionReason) : MoneyTransferStatus() {
    override fun equals(other: Any?): Boolean = other is Rejected && this.rejectionReason == other.rejectionReason
  }
}
