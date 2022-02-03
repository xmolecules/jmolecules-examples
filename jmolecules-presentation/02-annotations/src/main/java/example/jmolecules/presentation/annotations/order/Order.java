/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.jmolecules.presentation.annotations.order;

import example.jmolecules.presentation.annotations.customer.Customer;
import example.jmolecules.presentation.annotations.customer.Customer.CustomerId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jmolecules.ddd.annotation.Identity;

/**
 * @author Oliver Drotbohm
 */
@org.jmolecules.ddd.annotation.AggregateRoot
@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "SAMPLE_ORDER")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Order {

	private final @Identity @EmbeddedId OrderId id;
	private @OneToMany(cascade = CascadeType.ALL) List<LineItem> lineItems;
	private CustomerId customer;

	public Order(Customer customer) {

		this.id = OrderId.of(UUID.randomUUID());
		this.customer = customer.getId();
	}

	@Value
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	public static class OrderId implements Serializable {

		private static final long serialVersionUID = -7839120919179668566L;
		private final UUID orderId;
	}
}
