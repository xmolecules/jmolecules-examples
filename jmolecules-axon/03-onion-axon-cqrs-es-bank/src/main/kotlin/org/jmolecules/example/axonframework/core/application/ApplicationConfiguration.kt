package org.jmolecules.example.axonframework.core.application

import org.jmolecules.architecture.onion.classical.ApplicationServiceRing
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
@ApplicationServiceRing
class ApplicationConfiguration
