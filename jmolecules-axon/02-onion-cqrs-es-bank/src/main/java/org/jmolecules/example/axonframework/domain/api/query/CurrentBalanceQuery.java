package org.jmolecules.example.axonframework.domain.api.query;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;

import java.util.Optional;

public record CurrentBalanceQuery(
  String accountId
) {
  public static ResponseType<Optional<CurrentBalanceResponse>> RESPONSE_TYPE = ResponseTypes.optionalInstanceOf(CurrentBalanceResponse.class);
}
