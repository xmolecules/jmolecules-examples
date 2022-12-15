package org.jmolecules.example.axonframework.core.usecase.moneytransfer

import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId
import java.util.function.Supplier

interface MoneyTransferIdGenerator : Supplier<MoneyTransferId>
