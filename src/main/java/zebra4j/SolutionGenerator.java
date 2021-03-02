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

import static zebra4j.Attributes.DEFAULT_TYPES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SolutionGenerator implements Supplier<PuzzleSolution> {

	private final Set<AttributeType> attributeTypes;
	private final int numPeople;
	private final Random rnd;

	public SolutionGenerator() {
		this(DEFAULT_TYPES.size());
	}

	public SolutionGenerator(int numPeople) {
		// TODO Generate random subset given number of people
		this(DEFAULT_TYPES, numPeople, new Random());
	}

	@Override
	public PuzzleSolution get() {
		List<List<Attribute>> attributesByPersonAndType = new ArrayList<>();
		List<AttributeType> attributeTypesSorted = new ArrayList<>(attributeTypes);

		// TODO reconsider
		attributeTypesSorted.sort((a, b) -> a.toString().compareTo(b.toString()));

		Collections.swap(attributeTypesSorted, 0, attributeTypesSorted.indexOf(PersonName.TYPE));
		for (AttributeType type : attributeTypesSorted) {
			List<Attribute> attributes = type.getAttributes(numPeople);
			Collections.shuffle(attributes, rnd);
			attributesByPersonAndType.add(attributes);
		}
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder(true);
		for (int i = 0; i < numPeople; ++i) {
			final int person = i;
			List<Attribute> attributeList = attributesByPersonAndType.stream().map(l -> l.get(person))
					.collect(Collectors.toList());
			builder.add(new SolutionPerson(attributeList));
		}
		return builder.build();
	}

	public PuzzleSolution generate() {
		return get();
	}
}
