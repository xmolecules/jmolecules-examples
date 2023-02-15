package org.jmolecules.example.axonframework.bank.application.port.out.event

import org.jmolecules.architecture.hexagonal.SecondaryPort
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferFailedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferredEvent

/**
 * Port to send milestone events.
 */
@SecondaryPort
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
