package org.jmolecules.example.axonframework.infrastructure.jackson

import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Type info for all classes using inheritance.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class", include = JsonTypeInfo.As.PROPERTY)
class KotlinTypeInfo
