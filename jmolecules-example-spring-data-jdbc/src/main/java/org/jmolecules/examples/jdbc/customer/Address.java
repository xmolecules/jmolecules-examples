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
package org.jmolecules.examples.jdbc.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.data.annotation.PersistenceCreator;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceCreator))
public class Address implements Entity<Customer, Address.AddressId> {

	private final AddressId id;
	private final String street, city, zipCode;

	public Address(String street, String city, String zipCode) {

		this.id = new AddressId(UUID.randomUUID());
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}

	public record AddressId(UUID id) implements Identifier {}
}
