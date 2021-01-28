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
import lombok.extern.slf4j.Slf4j;
import zebra4j.fact.BothTrue;
import zebra4j.fact.Different;
import zebra4j.fact.Fact;
import zebra4j.fact.NearbyHouse;

@AllArgsConstructor
@Slf4j
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
		int solutionsCnt = countSolutions(puzzle);
		if (solutionsCnt != 1) {
			String msg = String.format("The provided set of fact types generated a puzzle %s with %s solutions. "
					+ "Amend the facts so the solutions are exactly 1.", puzzle, solutionsCnt);
			log.trace("{} using {} facts.", msg, facts.size());
			throw new IllegalArgumentException(msg);
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
			P puzzle = toPuzzle(factsCopy);
			if (countSolutions(puzzle) == 1) {
				facts.remove(i);
				--i;
			}
		}

	}

}
