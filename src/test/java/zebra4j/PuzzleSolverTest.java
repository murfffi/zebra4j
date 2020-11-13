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

import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PuzzleSolverTest {

	@Test
	public void testUnique() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.sampleSolution();
		Puzzle puzzle = new PuzzleGenerator(startSolution).generate();
		List<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertEquals(result.size(), new HashSet<>(result).size());
	}

	@Test
	public void testCriminal() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Puzzle puzzle = new PuzzleGenerator(startSolution).generate();
		List<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertEquals(result.size(), new HashSet<>(result).size());
	}

}
