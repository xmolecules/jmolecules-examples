package org.jmolecules.example.axonframework.domain.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.jmolecules.example.axonframework.domain.api.query.MoneyTransfer;
import org.jmolecules.example.axonframework.domain.api.query.MoneyTransferByIdQuery;
import org.jmolecules.example.axonframework.domain.api.query.MoneyTransfersQuery;
import org.jmolecules.example.axonframework.domain.api.query.MoneyTransfersResponse;
import org.jmolecules.example.axonframework.domain.event.transfer.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MoneyTransferProjection {

  private final Map<String, MoneyTransfer> store = new ConcurrentHashMap<>();

  @QueryHandler
  public Optional<MoneyTransfer> query(MoneyTransferByIdQuery query) {
    return Optional.ofNullable(store.get(query.moneyTransferId()));
  }

  @QueryHandler
  public MoneyTransfersResponse query(MoneyTransfersQuery query) {
    var filtered = store.values().stream()
      .filter(it -> it.sourceAccountId().equals(query.accountId()) || it.targetAccountId().equals(query.accountId()))
      .toList();

    return new MoneyTransfersResponse(filtered);
  }

  @EventHandler
  public void on(MoneyTransferRequestedEvent evt) {
    store.put(evt.moneyTransferId(), new MoneyTransfer(evt.moneyTransferId(), evt.sourceAccountId(), evt.targetAccountId(), evt.amount()));
  }

  @EventHandler
  public void on(MoneyTransferCompletedEvent evt) {
    store.computeIfPresent(evt.moneyTransferId(), (s, moneyTransfer) ->
      new MoneyTransfer(moneyTransfer.moneyTransferId(), moneyTransfer.sourceAccountId(), moneyTransfer.targetAccountId(),moneyTransfer.amount(), null)
    );
  }

  @EventHandler
  public void on(MoneyTransferCancelledEvent evt) {
    store.computeIfPresent(evt.moneyTransferId(), (s, moneyTransfer) ->
      new MoneyTransfer(moneyTransfer.moneyTransferId(), moneyTransfer.sourceAccountId(), moneyTransfer.targetAccountId(),moneyTransfer.amount(), evt.reason())
    );
  }

  MoneyTransfersResponse findAll() {
    return new MoneyTransfersResponse(store.values());
  }
}
