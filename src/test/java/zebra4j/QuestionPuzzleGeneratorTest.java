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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import zebra4j.fact.BothTrue;
import zebra4j.fact.Fact;
import zebra4j.util.JDKRandom;

public class QuestionPuzzleGeneratorTest {

	@Test
	public void testGenerate_NoExtra() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		QuestionPuzzle puzzle = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution,
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		Collection<Attribute> result = new QuestionPuzzleSolver(puzzle).solve();
		assertEquals(1, result.size());

		Collection<Fact> facts = puzzle.getPuzzle().getFacts();
		for (Fact f : facts) {
			Collection<Fact> lessFacts = new HashSet<>(facts);
			assertTrue(lessFacts.remove(f));
			QuestionPuzzle lessPuzzle = new QuestionPuzzle(puzzle.getQuestion(),
					new Puzzle(puzzle.getPuzzle().getAttributeSets(), lessFacts));
			long count = new QuestionPuzzleSolver(lessPuzzle).solveToStream().distinct().limit(2).count();
			assertEquals(2, count);
		}
	}

	@Test
	public void testGenerate_FactsDontAnswerDirectly() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		QuestionPuzzle puzzle = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution, new JDKRandom(1),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		Attribute nameOfCriminal = startSolution.findPerson(Criminal.YES).get().findAttribute(PersonName.TYPE);
		Collection<Fact> facts = puzzle.getPuzzle().getFacts();
		assertFalse(facts.contains(new BothTrue(nameOfCriminal, Criminal.YES)));
		assertFalse(facts.contains(new BothTrue(Criminal.YES, nameOfCriminal)));
	}

	@Test
	public void testGenerate_Stable() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		QuestionPuzzle puzzle1 = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution, new JDKRandom(1),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		QuestionPuzzle puzzle2 = new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL, startSolution, new JDKRandom(1),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		assertEquals(puzzle1, puzzle2);
	}

}
