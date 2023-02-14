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
package example.jmolecules.presentation.traditional;

import static org.assertj.core.api.Assertions.*;

import example.jmolecules.presentation.traditional.customer.Address;
import example.jmolecules.presentation.traditional.customer.Customer;
import example.jmolecules.presentation.traditional.customer.CustomerManagement;
import example.jmolecules.presentation.traditional.customer.Customers;
import example.jmolecules.presentation.traditional.order.Order;
import example.jmolecules.presentation.traditional.order.Orders;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.modulith.docs.Documenter;

/**
 * @author Oliver Drotbohm
 */
@SpringBootTest
@RequiredArgsConstructor
class ApplicationIntegrationTest {

	private final ConfigurableApplicationContext context;

	@Test
	void bootstrapsContainer() {

		assertThat(AssertableApplicationContext.get(() -> context)) //
				.hasSingleBean(CustomerManagement.class)
				.satisfies(ctx -> {

					ctx.publishEvent(new CustomerManagement.SampleEvent());

					CustomerManagement bean = ctx.getBean(CustomerManagement.class);

					assertThat(bean.eventReceived).isTrue();
				});
	}

	@Test
	void exposesPersistenceComponents() {

		var address = new Address("41 Greystreet", "Dreaming Tree", "2731");

		var customers = context.getBean(Customers.class);
		var customer = customers.save(new Customer("Dave", "Matthews", address));

		var orders = context.getBean(Orders.class);
		var order = orders.save(new Order(customer));

		assertThat(customers.findRequiredById(order.getCustomer())).isNotNull();
	}

	@Test
	void generateDocumentation() throws Exception {

		new Documenter(Application.class).writeDocumentation();
	}
}
