package org.jmolecules.examples.kotlin

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jmolecules.ddd.types.AggregateRoot
import org.jmolecules.ddd.types.Identifiable
import org.jmolecules.ddd.types.Identifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.CrudRepository
import java.util.*

@SpringBootApplication
open class KotlinExample

    fun main(args: Array<String>) {

        val context = runApplication<KotlinExample>(*args);

        val repository = context.getBean(OrderRepository::class.java);
        val order = repository.save(Order());
    }

    @Table(name = "KotlinOrder")
    open class Order : AggregateRoot<Order, Order.OrderIdentifier> {

        override val id = OrderIdentifier(UUID.randomUUID())

        data class OrderIdentifier(val id: UUID) : Identifier
    }

    interface OrderRepository : CrudRepository<Order, Order.OrderIdentifier>
