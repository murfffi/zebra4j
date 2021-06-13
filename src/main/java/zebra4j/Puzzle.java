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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Value;
import zebra4j.fact.Fact;

/**
 * A puzzle that asks all attributes in {@link #getAttributeSets()} to be
 * assigned to people, satisfying rules defined by the attribute sets used in
 * the puzzle (e.g. all people have different name and the name may be Liza,
 * John or Mary). The assignment must also satisfy the given set of
 * {@link Fact}s.
 * 
 * <p>
 * The puzzle is immutable if the provided input collections are immutable.
 * 
 * <p>
 * For efficiency, the puzzle constructor does not validate if the attribute
 * sets and facts are consistent. Use {@link PuzzleBuilder} is validation is
 * preferred.
 */
@Value
public class Puzzle {

	/**
	 * The attribute types and the sub-sets of their values used in the puzzle.
	 */
	private final Map<AttributeType, Set<Attribute>> attributeSets;

	/**
	 * The facts that must be satisfied by any solution of the puzzle.
	 */
	private final Collection<Fact> facts;

	/**
	 * @return the number of people in the puzzle
	 */
	public int numPeople() {
		return attributeSets.entrySet().stream().filter(e -> e.getKey() instanceof AllDifferentType)
				.mapToInt(e -> e.getValue().size()).min().orElse(0);
	}

	/**
	 * @param attr required
	 * @return if the puzzle refers to the given attribute
	 */
	public boolean contains(Attribute attr) {
		return attributeSets.getOrDefault(attr.type(), Collections.emptySet()).contains(attr);
	}

	/**
	 * Describe in natural language the constraints that the solution must satisfy.
	 * 
	 * @param locale required
	 * @return a list of sentences in the given locale, not null.
	 */
	public List<String> describeConstraints(Locale locale) {
		return describeConstraintsForTypes(locale, attributeSets.keySet());
	}

	List<String> describeConstraintsForTypes(Locale locale, Set<AttributeType> types) {
		Stream<String> setDesc = types.stream().map(t -> t.describeSet(attributeSets.get(t), locale));
		Stream<String> factDesc = facts.stream().map(fact -> fact.describe(locale));
		return Stream.concat(setDesc, factDesc).collect(Collectors.toList());
	}
}
