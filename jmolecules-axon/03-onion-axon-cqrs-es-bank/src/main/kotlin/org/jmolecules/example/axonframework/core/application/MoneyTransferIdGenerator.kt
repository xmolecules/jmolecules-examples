package org.jmolecules.example.axonframework.core.application

import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import java.util.function.Supplier

interface MoneyTransferIdGenerator : Supplier<MoneyTransferId>
