/*
 * Copyright 2024 the original author or authors.
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
package example;

import static org.assertj.core.api.Assertions.*;

import org.jmolecules.archunit.JMoleculesDddRules;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(packagesOf = MyAggregateRoot.class)
class ArchitectureTests {

	@ArchTest
	void detectsArchitectureViolation(JavaClasses classes) {

		var violations = JMoleculesDddRules.all().evaluate(classes);

		assertThat(violations.getFailureReport().getDetails()).hasSize(1)
				.element(0).asString()
				.contains("Field", "aggregate", "Association");
	}
}
