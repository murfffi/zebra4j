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

import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;

import zebra4j.Cli.GeneratedBasicPuzzle;
import zebra4j.Cli.GeneratedQuestionPuzzle;

/**
 * Sample app
 */
class Demo {

	private static final int NUM_PEOPLE = 3;

	public static void main(String[] args) {
		demo();
	}

	static void demo() {
		questionPuzzle();
		basicPuzzle();
		customQuestionPuzzle();
	}

	/**
	 * Generates and solves a basic {@link Puzzle} with default configuration
	 */
	public static void basicPuzzle() {
		System.out.println("Basic puzzle:");
		Puzzle puzzle = PuzzleGenerator.randomPuzzle(NUM_PEOPLE);
		PuzzleSolution solution = new PuzzleSolver(puzzle).solve().get(0);
		Cli.printGeneratedBasicPuzzle(new GeneratedBasicPuzzle(null, puzzle, solution), Locale.getDefault(),
				System.out);
	}

	/**
	 * Generates and solves a {@link QuestionPuzzle} with default configuration
	 */
	public static void questionPuzzle() {
		System.out.println("Question puzzle:");
		QuestionPuzzle puzzle = QuestionPuzzleGenerator.randomPuzzle(NUM_PEOPLE);
		Attribute solution = new QuestionPuzzleSolver(puzzle).solveSingle();
		Cli.printGeneratedQuestionPuzzle(new GeneratedQuestionPuzzle(null, puzzle, solution), Locale.getDefault(),
				System.out);
	}

	/**
	 * Generates and solves a {@link QuestionPuzzle} with custom configuration
	 */
	public static void customQuestionPuzzle() {
		System.out.println("Custom question puzzle:");
		// Check out the definition of Attributes.PET to learn the easiest way to define
		// your own attributes.
		Set<AttributeType> attributeTypes = SetUtils.unmodifiableSet(Attributes.PET, Attributes.NAME,
				Attributes.AT_HOUSE);
		PuzzleSolution sampleSolution = new SolutionGenerator(attributeTypes, NUM_PEOPLE, new Random()).generate();
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(Question.generate(sampleSolution),
				sampleSolution, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES);
		QuestionPuzzle puzzle = generator.generate();
		Attribute solution = new QuestionPuzzleSolver(puzzle).solveSingle();
		Cli.printGeneratedQuestionPuzzle(new GeneratedQuestionPuzzle(null, puzzle, solution), Locale.getDefault(),
				System.out);
	}

}
