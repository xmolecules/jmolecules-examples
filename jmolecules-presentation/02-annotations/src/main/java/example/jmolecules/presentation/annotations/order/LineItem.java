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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.EmbeddedId;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Entity
@javax.persistence.Entity
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class LineItem {

	private final @Identity @EmbeddedId LineItemId id;

	@Value(staticConstructor = "of")
	public static class LineItemId implements Serializable {

		private static final long serialVersionUID = -8693876096950090353L;
		private final UUID id;

		public static LineItem.LineItemId create() {
			return LineItemId.of(UUID.randomUUID());
		}
	}
}
