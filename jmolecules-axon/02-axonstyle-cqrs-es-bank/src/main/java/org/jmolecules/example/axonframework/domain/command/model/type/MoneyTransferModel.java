package org.jmolecules.example.axonframework.domain.command.model.type;

import java.util.HashMap;
import java.util.Map;

public record MoneyTransferModel(
  Map<String, Integer> activeMoneyTransfers
) {

  public int getReservedAmount() {
    return activeMoneyTransfers.values().stream().mapToInt(Integer::intValue).sum();
  }

  public int getAmountForTransfer(String moneyTransferId) {
    return activeMoneyTransfers.get(moneyTransferId);
  }

  public MoneyTransferModel add(String moneyTransferId, int amount) {
    final HashMap<String, Integer> copy = new HashMap<>(activeMoneyTransfers);
    copy.put(moneyTransferId, amount);
    return new MoneyTransferModel(copy);
  }

  public MoneyTransferModel remove(String moneyTransferId) {
    final HashMap<String, Integer> copy = new HashMap<>(activeMoneyTransfers);
    copy.remove(moneyTransferId);
    return new MoneyTransferModel(copy);
  }

  public boolean hasMoneyTransfer(String moneyTransferId) {
    return activeMoneyTransfers.containsKey(moneyTransferId);
  }

  public Map<String, Integer> getActiveMoneyTransfers() {
    return activeMoneyTransfers;
  }
}
