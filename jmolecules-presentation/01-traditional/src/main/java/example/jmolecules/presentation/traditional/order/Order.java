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
package example.jmolecules.presentation.traditional.order;

import example.jmolecules.presentation.traditional.customer.Customer;
import example.jmolecules.presentation.traditional.customer.Customer.CustomerId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author Oliver Drotbohm
 */
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Table(name = "SAMPLE_ORDER")
@Getter
@RequiredArgsConstructor
public class Order {

	private final @EmbeddedId OrderId id;

	@OneToMany(cascade = CascadeType.ALL) //
	private List<LineItem> lineItems;
	private CustomerId customer;

	public Order(Customer customer) {

		this.id = OrderId.of(UUID.randomUUID());
		this.customer = customer.getId();
	}

	@Value
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true)
	public static class OrderId implements Serializable {
		private static final long serialVersionUID = -8210019331014617255L;
		private final UUID orderId;
	}
}
