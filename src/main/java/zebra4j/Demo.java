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

import java.util.Random;

import org.apache.commons.collections4.SetUtils;

/**
 * Sample app
 */
public class Demo {

	private static final int NUM_PEOPLE = 3;

	public static void main(String[] args) {
		basicPuzzle();
		questionPuzzle();
		customQuestionPuzzle();
	}

	public static void basicPuzzle() {
		System.out.println("Basic puzzle:");
		Puzzle puzzle = PuzzleGenerator.randomPuzzle(NUM_PEOPLE);
		System.out.println(puzzle);
		System.out.println(new PuzzleSolver(puzzle).solve());
	}

	public static void questionPuzzle() {
		System.out.println("Question puzzle:");
		QuestionPuzzle puzzle = QuestionPuzzleGenerator.randomPuzzle(NUM_PEOPLE);
		System.out.println(puzzle);
		System.out.println(new QuestionPuzzleSolver(puzzle).solve().get(0).description());
	}

	public static void customQuestionPuzzle() {
		System.out.println("Custom question puzzle:");
		// Check out the definition of Attributes.PET to learn the easiest way to define
		// your own attributes.
		PuzzleSolution sampleSolution = new SolutionGenerator(
				SetUtils.unmodifiableSet(Attributes.PET, Attributes.NAME, Attributes.AT_HOUSE), NUM_PEOPLE,
				new Random()).generate();
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(Question.generate(sampleSolution),
				sampleSolution, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES);
		QuestionPuzzle puzzle = generator.generate();
		System.out.println(puzzle);
		System.out.println(new QuestionPuzzleSolver(puzzle).solve().get(0).description());
	}

}
