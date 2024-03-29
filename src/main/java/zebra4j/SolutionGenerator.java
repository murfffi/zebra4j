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

import static zebra4j.Attributes.DEFAULT_TYPES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.concurrent.ThreadSafe;

import lombok.AllArgsConstructor;
import zebra4j.util.JDKRandom;
import zebra4j.util.Randomness;

/**
 * Generator of {@link PuzzleSolution}
 */
@AllArgsConstructor
@ThreadSafe
public class SolutionGenerator implements Supplier<PuzzleSolution> {

	/**
	 * The types of attributes to be used in the generated puzzles
	 */
	private final Set<AttributeType> attributeTypes;

	/**
	 * The number of people in the generated puzzles
	 */
	private final int numPeople;
	private final Randomness rnd;

	/**
	 * Creates a generator with default configuration and largest possible puzzle
	 * size
	 */
	public SolutionGenerator() {
		this(DEFAULT_TYPES.size());
	}

	/**
	 * Creates a generator with default configuration
	 * 
	 * @param numPeople the number of people in the generated puzzles
	 */
	public SolutionGenerator(int numPeople) {
		// TODO Generate random subset of attribute typed given number of people
		this(DEFAULT_TYPES, numPeople, new JDKRandom());
	}

	@Override
	public PuzzleSolution get() {
		List<List<Attribute>> attributesByPersonAndType = new ArrayList<>();
		List<AttributeType> attributeTypesSorted = new ArrayList<>(attributeTypes);

		// Sort attribute types by name to achieve reproducible (stable) generation.
		attributeTypesSorted.sort((a, b) -> a.toString().compareTo(b.toString()));

		// Make sure name is first, so if facts are generated from the solution, the
		// name is always "to the left".
		Collections.swap(attributeTypesSorted, 0, attributeTypesSorted.indexOf(PersonName.TYPE));

		for (AttributeType type : attributeTypesSorted) {
			List<Attribute> attributes = type.getAttributes(numPeople);
			rnd.shuffle(attributes);
			attributesByPersonAndType.add(attributes);
		}

		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		for (int i = 0; i < numPeople; ++i) {
			final int person = i;
			List<Attribute> attributeList = attributesByPersonAndType.stream().map(l -> l.get(person))
					.collect(Collectors.toList());
			builder.add(new SolutionPerson(attributeList));
		}
		return builder.build();
	}

	/**
	 * Generates a new puzzle.
	 * 
	 * <p>
	 * Same as {@link #get()}.
	 * 
	 * @return a new random puzzle, not null
	 */
	public PuzzleSolution generate() {
		return get();
	}
}
