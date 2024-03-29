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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.concurrent.ThreadSafe;

import zebra4j.fact.Fact;
import zebra4j.util.JDKRandom;
import zebra4j.util.Randomness;

/**
 * A generator for {@link BasicPuzzle}
 * 
 * <p>
 * You can use the same generator to create multiple different puzzles by
 * calling the {@link #generate()} method multiple times. All generated puzzles
 * will have the same attributes and solution but will have different facts
 * (clues).
 */
@ThreadSafe
public class PuzzleGenerator extends AbstractPuzzleGenerator<BasicPuzzle> {

	/**
	 * Generate a new random puzzle using defaults
	 * 
	 * @param numPeople number of people in the puzzle
	 * @return a random puzzle
	 */
	public static BasicPuzzle randomPuzzle(int numPeople) {
		PuzzleGenerator generator = new PuzzleGenerator(new SolutionGenerator(numPeople).generate(),
				DEFAULT_FACT_TYPES);
		return generator.generate();
	}

	/**
	 * Generate a new random puzzle using default randomness source
	 * 
	 * @param solution  the generated puzzle will have this as a single solution
	 * @param factTypes
	 */
	public PuzzleGenerator(PuzzleSolution solution, Set<Fact.Type> factTypes) {
		this(new JDKRandom(), solution, factTypes);
	}

	public PuzzleGenerator(Randomness rnd, PuzzleSolution solution, Set<Fact.Type> factTypes) {
		super(rnd, solution, factTypes);
	}

	@Override
	protected BasicPuzzle toPuzzle(Collection<Fact> facts) {
		return new BasicPuzzle(solution.getAttributeSets(), facts);
	}

	@Override
	protected int countSolutions(BasicPuzzle puzzle) {
		return newSolver(puzzle).solve().size();
	}

	@Override
	protected boolean uniqueSolution(BasicPuzzle puzzle) {
		Iterator<PuzzleSolution> iter = solveToStream(puzzle).iterator();
		if (!iter.hasNext()) {
			return false;
		}
		PuzzleSolution first = iter.next();
		while (iter.hasNext()) {
			if (!first.equals(iter.next())) {
				return false;
			}
		}
		return true;
	}

	private Stream<PuzzleSolution> solveToStream(BasicPuzzle puzzle) {
		return newSolver(puzzle).solveToStream();
	}

	private PuzzleSolver newSolver(BasicPuzzle puzzle) {
		PuzzleSolver solver = new PuzzleSolver(puzzle);
		solver.setChocoSettings(getChocoSettings());
		return solver;
	}

}
