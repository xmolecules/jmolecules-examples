package org.jmolecules.example.axonframework.domain.api.query;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;

import java.util.Optional;

public record MoneyTransferByIdQuery(String moneyTransferId) {

  public static final ResponseType<Optional<MoneyTransfer>> RESPONSE_TYPE = ResponseTypes.optionalInstanceOf(MoneyTransfer.class);

}
