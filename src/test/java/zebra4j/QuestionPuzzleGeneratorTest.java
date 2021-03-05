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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import zebra4j.fact.BothTrue;
import zebra4j.fact.Fact;

public class QuestionPuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		QuestionPuzzle puzzle = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution,
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		Collection<Attribute> result = new QuestionPuzzleSolver(puzzle).solve();
		assertEquals(1, result.size());
	}

	@Test
	public void testGenerate_FactsDontAnswerDirectly() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		QuestionPuzzle puzzle = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution, new Random(1),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		Attribute nameOfCriminal = startSolution.findPerson(Criminal.YES).get().findAttribute(PersonName.TYPE);
		Set<Fact> facts = puzzle.getPuzzle().getFacts();
		assertFalse(facts.contains(new BothTrue(nameOfCriminal, Criminal.YES)));
		assertFalse(facts.contains(new BothTrue(Criminal.YES, nameOfCriminal)));
	}

	@Test
	public void testGenerate_Stable() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		QuestionPuzzle puzzle1 = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution, new Random(1),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		QuestionPuzzle puzzle2 = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution, new Random(1),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		assertEquals(puzzle1, puzzle2);
	}

}
