/*
 * Copyright 2022 the original author or authors.
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
package org.jmolecules.example.axonframework;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.axonframework.queryhandling.QueryHandler;
import org.jmolecules.architecture.cqrs.annotation.QueryModel;
import org.jmolecules.event.annotation.DomainEventHandler;

@Slf4j
@QueryModel
class SampleQueryModel {

	private final List<SampleEventOccurred> all = new ArrayList<>();

	@DomainEventHandler
	public void on(SampleEventOccurred event) {

		log.info("Event occurred" + event.toString());
		all.add(event);
	}

	@DomainEventHandler
	public void on(SampleRevokedEventOccurred event) {

		log.info("Revoke event occurred" + event.toString());
		findById(event.getIdentifier()).ifPresent(all::remove);
	}

	@QueryHandler
	public List<SampleEventOccurred> findByValue(String value) {

		return all.stream() //
				.filter(event -> event.getValue().equals(value)) //
				.collect(Collectors.toList());
	}

	@QueryHandler
	public Optional<SampleEventOccurred> findById(SampleAggregateIdentifier id) {

		return all.stream() //
				.filter(event -> event.getIdentifier().equals(id)) //
				.findFirst();
	}
}
