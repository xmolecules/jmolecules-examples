/*
 * Copyright 2021 the original author or authors.
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
package example.jmolecules.presentation.types;

import java.io.IOException;

import org.jmolecules.archunit.JMoleculesDddRules;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.docs.Documenter.CanvasOptions;
import org.springframework.modulith.docs.Documenter.DiagramOptions;
import org.springframework.modulith.docs.Documenter.DiagramOptions.DiagramStyle;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * @author Oliver Drotbohm
 */
@AnalyzeClasses(packagesOf = ArchitectureTests.class)
class ArchitectureTests {

	@ArchTest ArchRule ddd = JMoleculesDddRules.all();

	@Test
	void documentation() throws IOException {

		var diagramOptions = DiagramOptions.defaults().withStyle(DiagramStyle.C4);
		var canvasOptions = CanvasOptions.defaults();

		new Documenter(Application.class).writeDocumentation(diagramOptions, canvasOptions);
	}
}
