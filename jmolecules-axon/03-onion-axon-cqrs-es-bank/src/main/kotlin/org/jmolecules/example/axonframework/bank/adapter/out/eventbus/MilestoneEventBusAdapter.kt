package org.jmolecules.example.axonframework.bank.adapter.out.eventbus

import mu.KLogging
import org.axonframework.eventhandling.gateway.EventGateway
import org.jmolecules.architecture.hexagonal.SecondaryAdapter
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferFailedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferredEvent
import org.jmolecules.example.axonframework.bank.application.port.out.event.MoneyTransferEventPort
import org.springframework.stereotype.Component

/**
 * Event bus adapter for publishing milestone events.
 */
@Component
@SecondaryAdapter
class MilestoneEventBusAdapter(
  private val eventGateway: EventGateway
) : MoneyTransferEventPort {

  companion object : KLogging()

  override fun moneyTransferSucceeded(event: MoneyTransferredEvent) {
    eventGateway.publish(event)
    logger.info { "MoneyTransfer [${event.moneyTransferId}] finished with: $event" }
  }

  override fun moneyTransferFailed(event: MoneyTransferFailedEvent) {
    eventGateway.publish(event)
    logger.info { "MoneyTransfer [${event.moneyTransferId}] cancelled with" }
  }
}
