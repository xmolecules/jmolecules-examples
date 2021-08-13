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
package example.jmolecules.presentation.annotations.customer;

import example.jmolecules.presentation.annotations.customer.Customer.CustomerId;

import java.util.Optional;

import org.springframework.data.repository.Repository;

/**
 * @author Oliver Drotbohm
 */
@org.jmolecules.ddd.annotation.Repository
public interface Customers extends Repository<Customer, CustomerId> {

	Customer save(Customer customer);

	Optional<Customer> findById(CustomerId id);
}
