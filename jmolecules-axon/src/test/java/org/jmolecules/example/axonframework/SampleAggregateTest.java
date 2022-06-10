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

import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.Test;

class SampleAggregateTest {

	private final AggregateTestFixture<SampleEventSourcedAggregate> aggregate = new AggregateTestFixture<>(
			SampleEventSourcedAggregate.class);

	@Test
	void shouldCreateAggregate() {

		var id = SampleAggregateIdentifier.of(UUID.randomUUID().toString());

		aggregate
				.givenNoPriorActivity()
				.when(new PerformSampleCommand(id, "value"))
				.expectEvents(new SampleEventOccurred(id, "value"));
	}

	@Test
	void shouldDeliverRevokeToAggregate() {

		var id = SampleAggregateIdentifier.of(UUID.randomUUID().toString());

		aggregate
				.given(new SampleEventOccurred(id, "value"))
				.when(new RevokeSampleCommand(id))
				.expectEvents(new SampleRevokedEventOccurred(id));
	}
}
