/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 - 2021 Marin Nozhchev
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

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;
import org.junit.Test;

import zebra4j.fact.BothTrue;
import zebra4j.fact.Fact;

public class PuzzleTest {

	@Test
	public void testNumPeople() {
		PuzzleSolution solution = PuzzleGeneratorTest.sampleSolution();
		Puzzle puzzle = new Puzzle(solution.getAttributeSets(), Collections.emptySet());
		assertEquals(3, puzzle.numPeople());
	}

	@Test
	public void testNumPeopleWithCriminal() {
		PuzzleBuilder builder = new PuzzleBuilder();
		builder.addSet(Criminal.YES, Criminal.NO);
		builder.addSet(Clothes.BLUE, Clothes.RED, Clothes.GREEN);
		Puzzle puzzle = builder.build();
		assertEquals(3, puzzle.numPeople());
	}

	@Test
	public void testNumPeopleWithDifferentSizeSets() {
		Map<AttributeType, Set<Attribute>> sets = new LinkedHashMap<>();
		sets.put(Clothes.TYPE, SetUtils.unmodifiableSet(Clothes.BLUE, Clothes.RED, Clothes.GREEN));
		sets.put(AtHouse.TYPE, new HashSet<>(AtHouse.TYPE.getAttributes(2)));
		Puzzle puzzle = new Puzzle(sets, Collections.emptySet());
		assertEquals(2, puzzle.numPeople());
	}

	@Test
	public void testDescribeContstraints() throws Exception {
		PuzzleSolution solution = PuzzleGeneratorTest.sampleSolution();
		Set<Fact> facts = Collections.singleton(new BothTrue(PersonName.PETER, new AtHouse(1)));
		Puzzle puzzle = new Puzzle(solution.getAttributeSets(), facts);
		assertEquals(puzzle.getAttributeSets().size() + facts.size(),
				puzzle.describeConstraints(Locale.getDefault()).size());
	}
}
