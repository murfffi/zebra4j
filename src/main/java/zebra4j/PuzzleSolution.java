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

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.Value;

@Value
public class PuzzleSolution {

	private final Set<SolutionPerson> people;
	private final Map<AttributeType, Set<Attribute>> attributeSets;

	/**
	 * Find a person based on an attribute
	 * 
	 * @param attr
	 * @return the person, if any, that has this uniquely identifying attribute
	 */
	public Optional<SolutionPerson> findPerson(Attribute attr) {
		return people.stream().filter(person -> person.asList().contains(attr)).findAny();
	}
}
