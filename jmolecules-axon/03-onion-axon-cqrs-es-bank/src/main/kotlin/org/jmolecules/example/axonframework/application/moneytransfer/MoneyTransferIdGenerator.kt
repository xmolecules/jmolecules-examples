package org.jmolecules.example.axonframework.application.moneytransfer

import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
import java.util.function.Supplier

interface MoneyTransferIdGenerator : Supplier<MoneyTransferId>
