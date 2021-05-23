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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.Validate;

import lombok.extern.slf4j.Slf4j;
import zebra4j.fact.BothTrue;
import zebra4j.fact.Different;
import zebra4j.fact.Fact;
import zebra4j.util.CollectionChain;

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
@Slf4j
@ThreadSafe
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
	protected QuestionPuzzle toPuzzle(Collection<Fact> facts) {
		Validate.isTrue(question.appliesTo(solution), "Question %s does not apply to solution %s", question, solution);
		return new QuestionPuzzle(question, new Puzzle(solution.getAttributeSets(), facts));
	}

	@Override
	protected int countSolutions(QuestionPuzzle puzzle) {
		QuestionPuzzleSolver solver = new QuestionPuzzleSolver(puzzle);
		solver.setChocoSettings(getChocoSettings());
		// .solveToStream().distinct().count() may be more efficient but .distinct() is
		// flaky in TeaVM
		return solver.solve().size();
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
		// Avoid puzzles with simple exclusion: the answer is either A, B or C and is
		// not B or C.
		if (fact.getLeft().equals(question.getTowards()) && fact.getRight().type().equals(question.getAbout())) {
			return true;
		}
		if (fact.getLeft().type().equals(question.getAbout()) && fact.getRight().equals(question.getTowards())) {
			return true;
		}
		return false;
	}

	/**
	 * BothTrue facts are the most informative so many of those are rejected for
	 * question puzzles.
	 */
	private boolean rejectBothTrue(BothTrue fact) {
		if (fact.getLeft().equals(question.getTowards()) || fact.getRight().equals(question.getTowards())) {
			// I've already gone full circle once about if only (towards, answer) is
			// rejected or all BothTrue facts about towards or answer are rejected. So
			// far I know:

			// - BothTrue(towards, answer) should obviously be rejected
			// - Chains like (towards, X), (X, answer) should also be rejected. Same if
			// longer: (towards, X), (X, Y), (Y, answer).

			// The implementation rejects all (towards, X) however generated puzzles may
			// be too similar.
			// Alternatively, the generator can select at random if all (towards, X) or all
			// (X, answer) are rejected. The most complex option is to reject at random one
			// of the chain elements for each chain separately.

			return true;
		}

		return false;
	}

	/**
	 * Optimized facts pruning for {@link QuestionPuzzle}
	 * 
	 * <p>
	 * The implementation in the super class is correct for question puzzles but not
	 * optimal. The major optimization below is looking for a single solution in a
	 * puzzle with an additional fact that excludes the starting one. This is
	 * equivalent to iterating over solutions looking for one different than the
	 * starting solution but pushes the search down to the solver which is faster.
	 * 
	 * <p>
	 * A minor implementation is reducing the amount of allocations. This
	 * implementation uses a single solver, puzzle and does one less copy of the
	 * facts per iteration. That does not reduce - sometimes even increases - wall
	 * clock time likely because copies are very fast, while GC runs in parallel.
	 * However, overall CPU usage is reduced because GC has less to do. In
	 * environments with less efficient GC, like TeaVM or native, wall clock time is
	 * decreased.
	 */
	@Override
	protected void removeFacts(List<Fact> facts) {
		Attribute answer = question.answer(solution).get();
		Different contraFact = new Different(question.getTowards(), answer);
		Collection<Fact> contraFacts = new CollectionChain<>(facts, Collections.singleton(contraFact));
		QuestionPuzzle puzzle = toPuzzle(contraFacts);
		QuestionPuzzleSolver solver = new QuestionPuzzleSolver(puzzle);
		solver.setChocoSettings(getChocoSettings());
		int step = 16;
		int iterations = 0;
		for (int i = 0; i < facts.size();) {
			++iterations;
			if (i + step >= facts.size()) {
				step = 1;
			}
			List<Fact> subList = facts.subList(i, i + step); // view
			List<Fact> removed = new ArrayList<>(subList);
			// very fast in an array list
			subList.clear();
			if (solver.solveToStream().findAny().isPresent()) {
				// this case is very rare if step = 1: out of the thousands of facts, usually
				// <10 are kept
				facts.addAll(i, removed);
				if (step > 1) {
					step /= 2;
				} else {
					++i;
				}
			}
		}
		log.debug("Solved in {} iterations.", iterations);
	}

}
