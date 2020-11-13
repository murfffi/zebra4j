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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator extends AbstractPuzzleGenerator<Puzzle> {

	public static Puzzle randomPuzzle(int numPeople) {
		PuzzleGenerator generator = new PuzzleGenerator(new SolutionGenerator(numPeople).generate());
		return generator.generate();
	}

	public PuzzleGenerator(PuzzleSolution solution) {
		this(new Random(), solution);
	}

	public PuzzleGenerator(Random rnd, PuzzleSolution solution) {
		super(rnd, solution);
	}

	@Override
	protected Puzzle toPuzzle(List<Fact> facts) {
		return toBasicPuzzle(solution, facts);
	}

	public static Puzzle toBasicPuzzle(PuzzleSolution solution, List<Fact> facts) {
		return new Puzzle(solution.getAttributeSets(), new LinkedHashSet<>(facts));
	}

	@Override
	protected CountingSolver createSolver(Puzzle puzzle) {
		return new PuzzleSolver(puzzle);
	}

}
