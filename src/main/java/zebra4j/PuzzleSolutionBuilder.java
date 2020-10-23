/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 Marin Nozhchev
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package zebra4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PuzzleSolutionBuilder {

	private final List<SolutionPerson> people = new ArrayList<>();
	private final Map<AttributeType, Set<Attribute>> attributeSets = new LinkedHashMap<>();
	private final boolean validating;

	public PuzzleSolutionBuilder() {
		this(true);
	}

	public PuzzleSolutionBuilder add(Attribute... attributes) {
		return add(new SolutionPerson(attributes));
	}

	public PuzzleSolutionBuilder add(SolutionPerson person) {
		if (validating && !attributeSets.isEmpty() && !getAttributeTypes().equals(person.attributeTypes())) {
			throw new IllegalArgumentException("People must have the same attributes.");
		}
		addAttributes(person.asList());

		people.add(person);
		return this;
	}

	private void addAttributes(List<Attribute> attributes) {
		for (Attribute attr : attributes) {
			attributeSets.putIfAbsent(attr.type(), new LinkedHashSet<>());
			boolean added = attributeSets.get(attr.type()).add(attr);
			if (!added && attr.type().checkDifferent()) {
				throw new IllegalArgumentException("Attributes must be different.");
			}
		}

	}

	private Set<AttributeType> getAttributeTypes() {
		return attributeSets.keySet();
	}

	public PuzzleSolutionBuilder addWithHouse(Attribute... attributes) {
		return addWithHouse(new SolutionPerson(attributes));
	}

	public PuzzleSolutionBuilder addWithHouse(SolutionPerson person) {
		if (person.attributeTypes().contains(AtHouse.TYPE)) {
			throw new IllegalArgumentException("AtHouse is not allowed to be pre-set in this case.");
		}
		SolutionPerson personWithHouse = person.withAttribute(new AtHouse(people.size() + 1));
		return add(personWithHouse);
	}

	public PuzzleSolution build() {
		return new PuzzleSolution(new LinkedHashSet<>(people), attributeSets);
	}

}
