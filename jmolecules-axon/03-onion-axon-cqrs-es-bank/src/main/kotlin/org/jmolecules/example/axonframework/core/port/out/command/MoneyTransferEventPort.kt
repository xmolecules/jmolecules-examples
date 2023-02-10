package org.jmolecules.example.axonframework.core.port.out.command

import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferFailedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferredEvent

/**
 * Port to send milestone events.
 */
interface MoneyTransferEventPort {
  /**
   * Money transfer successful executed.
   */
  fun moneyTransferSucceeded(event: MoneyTransferredEvent)

  /**
   * Money transfer failed.
   */
  fun moneyTransferFailed(event: MoneyTransferFailedEvent)
}
