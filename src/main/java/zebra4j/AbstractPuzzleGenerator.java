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
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.SetUtils;

import lombok.AllArgsConstructor;
import zebra4j.fact.BothTrue;
import zebra4j.fact.Different;
import zebra4j.fact.Fact;
import zebra4j.fact.NearbyHouse;

@AllArgsConstructor
public abstract class AbstractPuzzleGenerator<P> {

	public static final Set<Fact.Type> DEFAULT_FACT_TYPES = SetUtils.unmodifiableSet(BothTrue.TYPE, Different.TYPE,
			NearbyHouse.TYPE);

	private final Random rnd;
	protected final PuzzleSolution solution;
	private final Set<Fact.Type> factTypes;

	/**
	 * @return a new puzzle generated from the seed solution and randomness source;
	 *         each generated puzzle is different and has a minimal set of facts
	 */
	public P generate() {
		List<Fact> facts = factTypes.stream().flatMap(type -> type.generate(solution).stream())
				.filter(fact -> !rejectFact(fact)).collect(Collectors.toList());
		P puzzle = toPuzzle(facts);
		if (!hasUniqueSolution(facts)) {
			throw new RuntimeException(String.format("Incomplete rule generation. Puzzle %s has %s solutions.", puzzle,
					countSolutions(puzzle)));
		}
		removeFacts(facts);
		return toPuzzle(facts);
	}

	/**
	 * Create a puzzle using the seed solution and the given facts
	 * 
	 * @param facts, not empty
	 * @return
	 */
	protected abstract P toPuzzle(List<Fact> facts);

	/**
	 * @param puzzle
	 * @return number of unique solutions of the given puzzle
	 */
	protected abstract int countSolutions(P puzzle);

	/**
	 * Checks if the fact is not compatible with the specific puzzle being generated
	 * 
	 * <p>
	 * By default, all facts are compatible and none are rejected.
	 * 
	 * @param fact
	 * @return if the fact is rejected
	 */
	protected boolean rejectFact(Fact fact) {
		return false;
	}

	private void removeFacts(List<Fact> facts) {
		Collections.shuffle(facts, rnd);
		for (int i = 0; i < facts.size(); ++i) {
			List<Fact> factsCopy = new ArrayList<>(facts);
			factsCopy.remove(i);
			if (hasUniqueSolution(factsCopy)) {
				facts.remove(i);
				--i;
			}
		}

	}

	private boolean hasUniqueSolution(List<Fact> facts) {
		P puzzle = toPuzzle(facts);
		return countSolutions(puzzle) == 1;
	}
}
