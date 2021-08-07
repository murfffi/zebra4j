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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zebra4j.fact.BothTrue;
import zebra4j.fact.Different;

public class PuzzleSolverTest {

	private static final Logger log = LoggerFactory.getLogger(PuzzleSolverTest.class);

	@Test
	public void testUnique() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.sampleSolution();
		BasicPuzzle puzzle = new BasicPuzzle(startSolution.getAttributeSets(),
				new LinkedHashSet<>(BothTrue.TYPE.generate(startSolution)));
		List<PuzzleSolution> result = createTestSolver(puzzle).solve();
		Set<PuzzleSolution> resultSet = new HashSet<>(result);
		Assert.assertEquals(result.size(), resultSet.size());
		boolean contains = resultSet.contains(startSolution);
		log.debug("Hashcode: {}, Result hashcode: {}, Result: {}", startSolution.hashCode(), result.get(0).hashCode(),
				result);
		Assert.assertTrue(contains);
	}

	@Test
	public void testCriminal() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		BasicPuzzle puzzle = new BasicPuzzle(startSolution.getAttributeSets(),
				new LinkedHashSet<>(Different.TYPE.generate(startSolution).subList(0, 2)));
		List<PuzzleSolution> result = createTestSolver(puzzle).solve();
		Set<PuzzleSolution> resultSet = new HashSet<>(result);
		Assert.assertEquals(result.size(), resultSet.size());
	}

	/**
	 * Allows customizing the solver in TeaVM or native
	 * 
	 * @param puzzle
	 * @return {@link PuzzleSolver} from puzzle
	 */
	protected PuzzleSolver createTestSolver(BasicPuzzle puzzle) {
		return new PuzzleSolver(puzzle);
	}

}
