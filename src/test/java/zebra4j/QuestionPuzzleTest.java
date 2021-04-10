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
import java.util.Locale;
import java.util.Set;

import org.junit.Test;

import zebra4j.fact.BothTrue;
import zebra4j.fact.Fact;

public class QuestionPuzzleTest {

	@Test
	public void testDescribeConstraints() {
		PuzzleSolution solution = PuzzleGeneratorTest.sampleSolution();
		assertEquals(3, solution.getAttributeSets().size());
		Set<Fact> facts = Collections.singleton(new BothTrue(PersonName.PETER, new AtHouse(1)));
		QuestionPuzzle puzzle = new QuestionPuzzle(new Question(PersonName.PETER, AtHouse.TYPE),
				new Puzzle(solution.getAttributeSets(), facts));
		assertEquals(2 + facts.size(), puzzle.describeConstraints(Locale.getDefault()).size());
	}

}
