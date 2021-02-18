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

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.sampleSolution();
		Puzzle puzzle = new PuzzleGenerator(startSolution, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		Collection<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertTrue(result.contains(startSolution));
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testGenerate_Criminal() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Puzzle puzzle = new PuzzleGenerator(startSolution, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		log.trace("Puzzle is {}", puzzle);
		Collection<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertEquals(1, result.size());
		PuzzleSolution solution = result.iterator().next();
		Assert.assertEquals(solution.toString(), 2, solution.getPeople().size());
		SolutionPerson criminal = startSolution.findPerson(Criminal.YES).get();
		Assert.assertTrue(solution.getPeople().contains(criminal));
	}

	public static PuzzleSolution sampleSolution() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.PETER, Clothes.BLUE);
		builder.addWithHouse(PersonName.GEORGE, Clothes.YELLOW);
		builder.addWithHouse(PersonName.IVAN, Clothes.GREEN);
		PuzzleSolution solution = builder.build();
		return solution;
	}

	public static PuzzleSolution simpleSolutionWithCriminal() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.PETER, Criminal.NO);
		builder.addWithHouse(PersonName.GEORGE, Criminal.YES);
		PuzzleSolution startSolution = builder.build();
		return startSolution;
	}

}
