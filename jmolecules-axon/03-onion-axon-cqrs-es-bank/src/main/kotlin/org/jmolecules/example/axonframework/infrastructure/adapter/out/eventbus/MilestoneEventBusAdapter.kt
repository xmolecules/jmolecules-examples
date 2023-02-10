package org.jmolecules.example.axonframework.infrastructure.adapter.out.eventbus

import mu.KLogging
import org.axonframework.eventhandling.gateway.EventGateway
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferFailedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferredEvent
import org.jmolecules.example.axonframework.core.port.out.command.MoneyTransferEventPort
import org.springframework.stereotype.Component

/**
 * Event bus adapter for publishing milestone events.
 */
@Component
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
