package org.jmolecules.example.axonframework.bank.domain.moneytransfer.type

import org.jmolecules.ddd.annotation.ValueObject

/**
 * Status of money transfer.
 */
@ValueObject
sealed class MoneyTransferStatus(
  val success: Boolean
) {
  /**
   * Successful money transfer.
   */
  object Succeeded : MoneyTransferStatus(success = true)

  /**
   * Rejected money transfer.
   */
  data class Rejected(val rejectionReason: RejectionReason) : MoneyTransferStatus(success = false)
}
