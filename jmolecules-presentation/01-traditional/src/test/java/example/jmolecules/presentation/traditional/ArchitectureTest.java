package example.jmolecules.presentation.traditional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.repository.Repository;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaParameterizedType;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.AbstractClassesTransformer;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ClassesTransformer;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

@AnalyzeClasses(packagesOf = Application.class)
class ArchitectureTest {

	@ArchTest
	void verifyAggregates(JavaClasses types) {

		var aggregates = new AggregatesExtractor();
		var aggregateTypes = aggregates.doTransform(types);

		all(aggregates)
				.should(notReferToOtherAggregates(aggregateTypes))
				.check(types);
	}

	/**
	 * A {@link ClassesTransformer} to identify aggregates within the codebase. The aggregates are detected from Spring
	 * Data repository declarations, inspecting the generics of those.
	 *
	 * @author Oliver Drotbohm
	 */
	static class AggregatesExtractor extends AbstractClassesTransformer<JavaClass> {

		protected AggregatesExtractor() {
			super("Aggregates");
		}

		/*
		 * (non-Javadoc)
		 * @see com.tngtech.archunit.lang.AbstractClassesTransformer#doTransform(com.tngtech.archunit.bank.domain.JavaClasses)
		 */
		@Override
		public Set<JavaClass> doTransform(JavaClasses collection) {

			return collection.stream()
					.filter(JavaClass::isInterface)
					.filter(it -> it.isAssignableTo(Repository.class))
					.flatMap(it -> detectAggregateOn(it).stream())
					.collect(Collectors.toSet());
		}

		Optional<JavaClass> detectAggregateOn(JavaClass repositoryInterface) {

			return repositoryInterface.getInterfaces().stream()
					.filter(JavaParameterizedType.class::isInstance)
					.map(JavaParameterizedType.class::cast)
					.filter(it -> it.toErasure().isAssignableTo(Repository.class))
					.findFirst()
					.map(it -> it.getActualTypeArguments().get(0))
					.map(JavaType::toErasure);
		}
	}

	/**
	 * Returns an {@link ArchCondition} that disallows types to declare fields that point to other aggregates than
	 * themselves.
	 *
	 * @param aggregateTypes must not be {@literal null}.
	 * @return
	 */
	static ArchCondition<JavaClass> notReferToOtherAggregates(Set<JavaClass> aggregateTypes) {

		return new ArchCondition<JavaClass>("not refer to other aggregates") {

			@Override
			public void check(JavaClass item, ConditionEvents events) {

				item.getFields().stream()
						.filter(it -> aggregateTypes.contains(it.getType()))
						.forEach(it -> {

							if (!it.getOwner().equals(it.getType())) {
								events.add(SimpleConditionEvent.violated(it,
										String.format("%s must not refer to other aggregate %s!", it, it.getType().getName())));
							}
						});
			}
		};
	}
}
