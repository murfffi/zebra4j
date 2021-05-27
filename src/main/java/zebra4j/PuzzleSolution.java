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

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.tuple.Pair;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A full solution to a basic "full assignment" puzzle
 */
@EqualsAndHashCode
@ToString
@Immutable
public class PuzzleSolution {

	/**
	 * The set of people with all of their attributed specified.
	 */
	@Getter
	private final Set<SolutionPerson> people;

	/**
	 * The sets of attributes referenced in the solution, grouped by type.
	 */
	@Getter
	private final Map<AttributeType, Set<Attribute>> attributeSets;

	private final Map<Attribute, SolutionPerson> attrToPerson;

	public PuzzleSolution(Set<SolutionPerson> people, Map<AttributeType, Set<Attribute>> attributeSets) {
		this.people = people;
		this.attributeSets = attributeSets;
		this.attrToPerson = people.stream()
				.flatMap(person -> person.asList().stream().map(attr -> Pair.of(attr, person)))
				.collect(Collectors.toMap(Pair::getLeft, Pair::getRight, (a, b) -> a));
	}

	/**
	 * Find a person based on an attribute
	 * 
	 * @param attr
	 * @return the person, if any, that has this uniquely identifying attribute
	 */
	public Optional<SolutionPerson> findPerson(Attribute attr) {
		return Optional.ofNullable(attrToPerson.get(attr));
	}

	/**
	 * Describe the solution in natural language
	 * 
	 * @param locale the locale of the natural language
	 * @return an array of persons; each person described as an array of attribute
	 *         descriptions
	 */
	public String[][] describe(Locale locale) {
		return people.stream().map(sp -> sp.describe(locale)).toArray(String[][]::new);
	}
}
