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

	public P generate() {
		List<Fact> facts = factTypes.stream().flatMap(type -> type.generate(solution).stream())
				.filter(fact -> !rejectFact(fact)).collect(Collectors.toList());
		P puzzle = toPuzzle(facts);
		if (!hasUniqueSolution(facts)) {
			throw new RuntimeException(String.format("Incomplete rule generation. Puzzle %s has %s solutions.", puzzle,
					createSolver(puzzle).countSolutions()));
		}
		removeFacts(facts);
		return toPuzzle(facts);
	}

	protected abstract P toPuzzle(List<Fact> facts);

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

	protected boolean hasUniqueSolution(List<Fact> facts) {
		P puzzle = toPuzzle(facts);
		return createSolver(puzzle).countSolutions() == 1;
	}

	protected abstract CountingSolver createSolver(P puzzle);

	protected boolean rejectFact(Fact fact) {
		return false;
	}
}
