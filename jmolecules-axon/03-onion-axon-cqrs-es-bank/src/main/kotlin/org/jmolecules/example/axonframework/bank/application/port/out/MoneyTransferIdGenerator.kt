package org.jmolecules.example.axonframework.bank.application.port.out

import org.jmolecules.architecture.hexagonal.SecondaryPort
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import java.util.function.Supplier

@SecondaryPort
fun interface MoneyTransferIdGenerator : Supplier<MoneyTransferId>
