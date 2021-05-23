/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 Marin Nozhchev
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package zebra4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Iterative builder for {@link PuzzleSolution}.
 */
@NotThreadSafe
public class PuzzleSolutionBuilder {

	private final List<SolutionPerson> people = new ArrayList<>();
	private final Map<AttributeType, Set<Attribute>> attributeSets = new LinkedHashMap<>();
	private final Set<Attribute> expectedAttributeTypes = new HashSet<>();

	/**
	 * Adds a new person with the given attributes to the solution
	 * 
	 * @param attributes may be empty
	 * @return the builder
	 */
	public PuzzleSolutionBuilder add(Attribute... attributes) {
		return add(new SolutionPerson(attributes));
	}

	/**
	 * Adds a new person
	 * 
	 * @param person required
	 * @return the builder
	 */
	public PuzzleSolutionBuilder add(SolutionPerson person) {
		Set<AttributeType> allDifferentTypes = person.attributeTypes().stream()
				.filter(a -> a instanceof AllDifferentType).collect(Collectors.toSet());
		if (!expectedAttributeTypes.isEmpty() && !expectedAttributeTypes.equals(allDifferentTypes)) {
			throw new IllegalArgumentException("People must have the same attributes.");
		}
		addAttributes(person.asList());

		people.add(person);
		return this;
	}

	/**
	 * Adds a new person in the next house
	 * 
	 * @param attributes the attributes without {@link AtHouse#TYPE}
	 * @return the builder
	 */
	public PuzzleSolutionBuilder addWithHouse(Attribute... attributes) {
		SolutionPerson person = new SolutionPerson(attributes);
		if (person.findAttribute(AtHouse.TYPE) != null) {
			throw new IllegalArgumentException("AtHouse is not allowed to be pre-set in this case.");
		}
		SolutionPerson personWithHouse = person.withAttribute(new AtHouse(people.size() + 1));
		return add(personWithHouse);
	}

	/**
	 * @return the completed solution
	 */
	public PuzzleSolution build() {
		return new PuzzleSolution(new LinkedHashSet<>(people), attributeSets);
	}

	private void addAttributes(List<Attribute> attributes) {
		for (Attribute attr : attributes) {
			attributeSets.putIfAbsent(attr.type(), new LinkedHashSet<>());
			boolean added = attributeSets.get(attr.type()).add(attr);
			if (!added && attr.type() instanceof AllDifferentType) {
				throw new IllegalArgumentException("Attributes must be different.");
			}
		}
	}

}
