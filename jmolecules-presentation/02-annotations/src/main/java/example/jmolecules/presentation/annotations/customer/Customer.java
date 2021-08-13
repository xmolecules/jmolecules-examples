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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.jmolecules.ddd.annotation.Identity;
import org.springframework.util.Assert;

/**
 * @author Oliver Drotbohm
 */
@org.jmolecules.ddd.annotation.AggregateRoot
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Customer {

	private final @Identity @EmbeddedId CustomerId id;
	private String firstname, lastname;
	private @OneToMany(cascade = CascadeType.ALL) List<Address> addresses;

	public Customer(String firstname, String lastname, Address address) {

		Assert.notNull(address, "Address must not be null!");

		this.id = CustomerId.of(UUID.randomUUID().toString());

		this.firstname = firstname;
		this.lastname = lastname;

		this.addresses = new ArrayList<>();
		this.addresses.add(address);
	}

	@Value
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	public static class CustomerId implements Serializable {

		private static final long serialVersionUID = 1708147585771440448L;
		private final String id;
	}
}
