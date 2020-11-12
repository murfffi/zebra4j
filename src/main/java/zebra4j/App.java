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

import lombok.extern.slf4j.Slf4j;

/**
 * Sample app
 */
@SuppressWarnings("unused")
@Slf4j
public class App {
	public static void main(String[] args) {
		basicPuzzle();
		questionPuzzle();
	}

	public static void basicPuzzle() {
		System.out.println("Basic puzzle:");
		PuzzleGenerator generator = new PuzzleGenerator();
		Puzzle puzzle = generator.generate(new SolutionGenerator(3).generate());
		System.out.println(puzzle);
		System.out.println(new PuzzleSolver(puzzle).solve());
	}

	public static void questionPuzzle() {
		System.out.println("Question puzzle:");
		PuzzleSolution sampleSolution = new SolutionGenerator(3).generate();
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(Question.generate(sampleSolution));
		QuestionPuzzle puzzle = generator.generate(sampleSolution);
		System.out.println(puzzle);
		System.out.println(new QuestionPuzzleSolver(puzzle).solve());
	}

	public static PuzzleSolution sampleSolution() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.ПЕТЪР, Clothes.СИНИ, Criminal.YES);
		builder.addWithHouse(PersonName.ГЕОРГИ, Clothes.ЖЪЛТИ, Criminal.NO);
		builder.addWithHouse(PersonName.ИВАН, Clothes.ЗЕЛЕНИ, Criminal.NO);
		return builder.build();
	}
}
