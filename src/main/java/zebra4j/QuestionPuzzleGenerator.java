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

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import zebra4j.fact.BothTrue;
import zebra4j.fact.Different;
import zebra4j.fact.Fact;

/**
 * A generator for {@link QuestionPuzzle}
 * 
 * <p>
 * You can use the same generator to create multiple different puzzles by
 * calling the {@link #generate()} method multiple times. All generated puzzles
 * will have the same question, attributes and solution but will have different
 * facts (clues). The generated puzzles will not contain facts the directly
 * answer the question.
 */
public class QuestionPuzzleGenerator extends AbstractPuzzleGenerator<QuestionPuzzle> {

	/**
	 * Generates a random puzzle seeded with a random solution, with all built-in
	 * facts and attributes.
	 * 
	 * @param numPeople the number of people in the puzzle
	 * @return a puzzle, not null
	 */
	public static QuestionPuzzle randomPuzzle(int numPeople) {
		PuzzleSolution sampleSolution = new SolutionGenerator(numPeople).generate();
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(Question.generate(sampleSolution),
				sampleSolution, DEFAULT_FACT_TYPES);
		return generator.generate();
	}

	private final Question question;

	/**
	 * Creates a new generator with a default random generator
	 * 
	 * @see #QuestionPuzzleGenerator(Question, PuzzleSolution, Random, Set)
	 */
	public QuestionPuzzleGenerator(Question question, PuzzleSolution solution, Set<Fact.Type> factTypes) {
		this(question, solution, new Random(), factTypes);
	}

	/**
	 * Creates a new generator for puzzles with the given solution assignment
	 * 
	 * @see #randomPuzzle(int) example usage
	 * 
	 * @param question
	 * @param solution  a full assignment of attributes to people, used for
	 *                  generating {@link Fact}s
	 * @param rnd       a random generator, which may be initialized with a fixed
	 *                  seed to get repeatable results
	 * @param factTypes the types of facts to be generated e.g.
	 *                  {@link AbstractPuzzleGenerator#DEFAULT_FACT_TYPES}
	 */
	public QuestionPuzzleGenerator(Question question, PuzzleSolution solution, Random rnd, Set<Fact.Type> factTypes) {
		super(rnd, solution, factTypes);
		Validate.isTrue(question.appliesTo(solution), "Question %s does not apply to solution %s", question, solution);
		this.question = question;
	}

	@Override
	protected QuestionPuzzle toPuzzle(List<Fact> facts) {
		Validate.isTrue(question.appliesTo(solution), "Question %s does not apply to solution %s", question, solution);
		return new QuestionPuzzle(question, PuzzleGenerator.toBasicPuzzle(solution, facts));
	}

	@Override
	protected int countSolutions(QuestionPuzzle puzzle) {
		return new QuestionPuzzleSolver(puzzle).solve().size();
	}

	@Override
	protected boolean rejectFact(Fact fact) {
		if (fact instanceof BothTrue && rejectBothTrue((BothTrue) fact)) {
			return true;
		}

		if (fact instanceof Different && rejectDifferent((Different) fact)) {
			return true;
		}

		return false;
	}

	private boolean rejectDifferent(Different fact) {
		return false;
	}

	private boolean rejectBothTrue(BothTrue fact) {
		if (fact.getLeft().equals(question.getTowards()) || fact.getRight().equals(question.getTowards())) {
			return true;
		}

		return false;
	}

}