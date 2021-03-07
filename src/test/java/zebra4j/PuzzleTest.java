/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 - 2021 Marin Nozhchev
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

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

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
	public void testDescribeContstraints() throws Exception {
		PuzzleSolution solution = PuzzleGeneratorTest.sampleSolution();
		Set<Fact> facts = Collections.singleton(new BothTrue(PersonName.PETER, new AtHouse(1)));
		Puzzle puzzle = new Puzzle(solution.getAttributeSets(), facts);
		assertEquals(puzzle.getAttributeSets().size() + facts.size(),
				puzzle.describeConstraints(Locale.getDefault()).size());
	}
}
