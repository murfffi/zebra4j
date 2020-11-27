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

import java.util.List;

import org.junit.Test;

public class QuestionPuzzleSolverTest {

	@Test
	public void testSolve() {
		Puzzle basicPuzzle = new PuzzleGenerator(PuzzleGeneratorTest.simpleSolutionWithCriminal(),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		QuestionPuzzle questionPuzzle = new QuestionPuzzle(Question.NAME_OF_CRIMINAL, basicPuzzle);
		QuestionPuzzleSolver solver = new QuestionPuzzleSolver(questionPuzzle);
		List<Attribute> solutionNames = solver.solve();
		assertEquals(1, solutionNames.size());
		assertEquals(PersonName.TYPE, solutionNames.get(0).type());
	}

}
