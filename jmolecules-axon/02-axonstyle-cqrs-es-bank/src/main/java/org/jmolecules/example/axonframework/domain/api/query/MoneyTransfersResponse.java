package org.jmolecules.example.axonframework.domain.api.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record MoneyTransfersResponse(List<MoneyTransfer> moneyTransfers) {

  public MoneyTransfersResponse(Collection<MoneyTransfer> moneyTransfers) {
    this(new ArrayList<>(moneyTransfers));
  }
}
