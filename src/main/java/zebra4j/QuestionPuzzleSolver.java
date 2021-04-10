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

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.chocosolver.solver.Settings;

/**
 * Solver for {@link QuestionPuzzle}
 */
public class QuestionPuzzleSolver {

	private final QuestionPuzzle qPuzzle;
	private final PuzzleSolver solver;

	public QuestionPuzzleSolver(QuestionPuzzle qPuzzle) {
		this.qPuzzle = qPuzzle;
		this.solver = new PuzzleSolver(qPuzzle.getPuzzle());
	}

	public void setChocoSettings(Settings chocoSettings) {
		solver.setChocoSettings(chocoSettings);
	}

	/**
	 * @return a set of solutions - answers to the question that satisfy a puzzle.
	 *         The list will be empty if the puzzle contains contradicting rules or
	 *         facts. Puzzles generated by {@link QuestionPuzzleGenerator} have a
	 *         single solution and the collection will have a single element.
	 */
	public Set<Attribute> solve() {
		// .distinct() here doesn't seem to work in TeaVM
		return this.solver.solve().stream().flatMap(solution -> toStream(qPuzzle.getQuestion().answer(solution)))
				.collect(Collectors.toSet());
	}

	/**
	 * This method is available in Java 9+
	 */
	static <T> Stream<T> toStream(Optional<T> opt) {
		if (opt.isPresent()) {
			return Stream.of(opt.get());
		} else {
			return Stream.empty();
		}
	}
}
