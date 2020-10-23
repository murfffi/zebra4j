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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lombok.AllArgsConstructor;
import zebra4j.Fact.BothTrue;

@AllArgsConstructor
public abstract class AbstractPuzzleGenerator<P> {

	private final Random rnd;

	public P generate(PuzzleSolution solution) {
		List<Fact> facts = new ArrayList<>();
		facts.addAll(generateBothTrue(solution));
		facts.addAll(generateDifferent(solution));
		if (!hasUniqueSolution(solution, facts)) {
			throw new RuntimeException(String.format("Incomplete rule generation. Puzzle has %s solutions.",
					createSolver(toPuzzle(solution, facts)).countSolutions()));
		}
		removeFacts(facts, solution);
		return toPuzzle(solution, facts);
	}

	protected abstract P toPuzzle(PuzzleSolution solution, List<Fact> facts);

	private void removeFacts(List<Fact> facts, PuzzleSolution solution) {
		Collections.shuffle(facts, rnd);
		for (int i = 0; i < facts.size(); ++i) {
			List<Fact> factsCopy = new ArrayList<>(facts);
			factsCopy.remove(i);
			if (hasUniqueSolution(solution, factsCopy)) {
				facts.remove(i);
				--i;
			}
		}

	}

	public boolean hasUniqueSolution(PuzzleSolution solution, List<Fact> facts) {
		P puzzle = toPuzzle(solution, facts);
		return createSolver(puzzle).countSolutions() == 1;
	}

	protected abstract CountingSolver createSolver(P puzzle);

	public Set<Fact> generateBothTrue(PuzzleSolution solution) {
		Set<Fact> result = new LinkedHashSet<>();
		for (SolutionPerson person : solution.getPeople()) {
			List<Attribute> attributes = person.asList();
			for (int i = 0; i < attributes.size(); ++i) {
				for (int j = i + 1; j < attributes.size(); ++j) {
					BothTrue fact = new BothTrue(attributes.get(i), attributes.get(j));
					checkAndAdd(result, fact);
				}
			}
		}
		return result;
	}

	private void checkAndAdd(Set<Fact> result, Fact fact) {
		if (!rejectFact(fact)) {
			result.add(fact);
		}
	}

	public Set<Fact> generateDifferent(PuzzleSolution solution) {
		Set<Fact> result = new LinkedHashSet<>();
		for (SolutionPerson person : solution.getPeople()) {
			List<Attribute> attributes = person.asList();
			for (int i = 0; i < attributes.size(); ++i) {
				for (int j = i + 1; j < attributes.size(); ++j) {
					Attribute other = attributes.get(j);
					for (Attribute different : solution.getAttributeSets().get(other.type())) {
						if (!different.equals(other)) {
							checkAndAdd(result, new Fact.Different(attributes.get(i), different));
						}
					}
				}
			}
		}
		return result;
	}

	protected boolean rejectFact(Fact fact) {
		return false;
	}
}
