package org.jmolecules.example.axonframework.infrastructure.adapter.policy

import mu.KLogging
import org.axonframework.commandhandling.CommandResultMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.gateway.EventGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.event.annotation.DomainEventPublisher
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransfer
import org.jmolecules.example.axonframework.core.model.moneytransfer.Reason
import org.jmolecules.example.axonframework.core.model.moneytransfer.command.CancelMoneyTransferCommand
import org.jmolecules.example.axonframework.core.model.moneytransfer.command.CompleteMoneyTransferCommand
import org.jmolecules.example.axonframework.core.model.moneytransfer.command.ReceiveMoneyTransferCommand
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.*

@Saga
class MoneyTransferSaga {

  companion object : KLogging()

  private lateinit var moneyTransfer: MoneyTransfer

  @StartSaga
  @SagaEventHandler(associationProperty = "moneyTransferId")
  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferRequestedEvent")
  fun on(evt: MoneyTransferRequestedEvent, commandGateway: CommandGateway) {

    logger.info { "MoneyTransfer [${evt.moneyTransferId}] started with: $evt" }

    moneyTransfer = MoneyTransfer(
      moneyTransferId = evt.moneyTransferId,
      sourceAccountId = evt.sourceAccountId,
      targetAccountId = evt.targetAccountId,
      amount = evt.amount
    )

    val receiveCmd = ReceiveMoneyTransferCommand(
      moneyTransfer.targetAccountId,
      moneyTransfer.moneyTransferId,
      moneyTransfer.amount
    )
    commandGateway.send<ReceiveMoneyTransferCommand, Any>(receiveCmd) { _, commandResultMessage: CommandResultMessage<*> ->
      if (commandResultMessage.isExceptional) {
        // on error cancel
        val cancelCmd = CancelMoneyTransferCommand(
          moneyTransferId = moneyTransfer.moneyTransferId,
          sourceAccountId = moneyTransfer.sourceAccountId,
          targetAccountId = moneyTransfer.targetAccountId,
          reason = Reason.reasonForMessage(commandResultMessage.exceptionResult().message)
        )
        commandGateway.sendAndWait<Any>(cancelCmd)
      }
    }
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferReceivedEvent")
  @SagaEventHandler(associationProperty = "moneyTransferId")
  fun on(evt: MoneyTransferReceivedEvent, commandGateway: CommandGateway) {
    logger.trace { "on($evt)" }
    val completeCmd = CompleteMoneyTransferCommand(
      sourceAccountId = moneyTransfer.sourceAccountId,
      moneyTransferId = moneyTransfer.moneyTransferId
    )
    commandGateway.send<CompleteMoneyTransferCommand, Any>(completeCmd) { _, commandResultMessage: CommandResultMessage<*> ->
      if (commandResultMessage.isExceptional) {
        // on error cancel
        val cancelCmd = CancelMoneyTransferCommand(
          moneyTransferId = moneyTransfer.moneyTransferId,
          sourceAccountId = moneyTransfer.sourceAccountId,
          targetAccountId = moneyTransfer.targetAccountId,
          reason = Reason.reasonForMessage(commandResultMessage.exceptionResult().message)
        )
        commandGateway.sendAndWait<Any>(cancelCmd)
      }
    }
  }

  @SagaEventHandler(associationProperty = "moneyTransferId")
  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCompletedEvent")
  @DomainEventPublisher(
    publishes = "axon.bank.MoneyTransferredEvent",
    type = DomainEventPublisher.PublisherType.EXTERNAL
  )
  fun on(evt: MoneyTransferCompletedEvent, eventGateway: EventGateway) {
    logger.trace { "on($evt)" }
    // Milestone event
    val transferredEvent = MoneyTransferredEvent(
      moneyTransferId = moneyTransfer.moneyTransferId,
      sourceAccountId = moneyTransfer.sourceAccountId,
      targetAccountId = moneyTransfer.targetAccountId,
      amount = moneyTransfer.amount
    )
    eventGateway.publish(transferredEvent)
    logger.info { "MoneyTransfer [${moneyTransfer.moneyTransferId}] finished with: $transferredEvent" }

    SagaLifecycle.end()
  }

  @SagaEventHandler(associationProperty = "moneyTransferId")
  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
  fun on(evt: MoneyTransferCancelledEvent, eventGateway: EventGateway) {
    logger.trace { "on($evt)" }
    logger.info { "MoneyTransfer [${moneyTransfer.moneyTransferId}] cancelled with" }
    SagaLifecycle.end()
  }

  override fun toString(): String {
    return moneyTransfer.toString()
  }
}
