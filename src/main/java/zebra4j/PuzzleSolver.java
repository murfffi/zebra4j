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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Settings;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import zebra4j.fact.Fact;

/**
 * Solver for {@link Puzzle}
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class PuzzleSolver {

	private final Puzzle puzzle;

	@Getter
	@Setter
	private Settings chocoSettings;

	/**
	 * Solve the puzzle returning a lazy stream to all solutions in a raw form,
	 * close to the underlying Choco solver model
	 * 
	 * @return a list of raw solutions - map from {@link Attribute} to person ID;
	 *         useful when just counting solutions
	 */
	Stream<Map<Attribute, Integer>> solveChoco() {
		ZebraModel zebraModel = toModel();
		log.debug("Solving puzzle {}", puzzle);
		for (Fact fact : puzzle.getFacts()) {
			fact.postTo(zebraModel);
		}
		Model model = zebraModel.getChocoModel();
		Solver solver = model.getSolver();
		Solution solution = new Solution(model);
		AbstractSpliterator<Map<Attribute, Integer>> spliterator = new AbstractSpliterator<Map<Attribute, Integer>>(
				Long.MAX_VALUE, 0) {

			@Override
			public boolean tryAdvance(Consumer<? super Map<Attribute, Integer>> action) {
				if (!solver.solve()) {
					return false;
				}
				solution.record();
				// We extract the values of only the relevant variables. Returning a copy of the
				// complete solution is not efficient because the solution contains much more
				// internal variables and data.
				// For question puzzles, this can be further improved by extracting only the
				// attributes for towards and about, but the optimization is currently not worth
				// the added complexity.
				Set<Entry<Attribute, IntVar>> variables = zebraModel.getVariableMap().entrySet();
				action.accept(variables.stream()
						.collect(Collectors.toMap(e -> e.getKey(), e -> solution.getIntVal(e.getValue()))));
				return true;
			}
		};
		return StreamSupport.stream(spliterator, false);
	}

	/**
	 * Solves the puzzles eagerly.
	 * 
	 * @return a list of all distinct solution.
	 */
	public List<PuzzleSolution> solve() {
		List<PuzzleSolution> zebraSolutions = solveToStream().distinct().collect(Collectors.toList());
		log.trace("Found {} distinct solutions", zebraSolutions.size());
		return zebraSolutions;
	}

	/**
	 * @return a lazy stream of all solutions with possible duplicates
	 */
	public Stream<PuzzleSolution> solveToStream() {
		// 16% of the typical CPU cost of this method is in the map.
		return solveChoco().map(this::fromChocoSolution);
	}

	private PuzzleSolution fromChocoSolution(Map<Attribute, Integer> choco) {
		int numPeople = puzzle.numPeople();
		@SuppressWarnings("unchecked")
		List<Attribute>[] allAttributes = new List[numPeople];
		for (int i = 0; i < allAttributes.length; ++i) {
			allAttributes[i] = new ArrayList<>();
		}
		choco.entrySet().stream().forEach(e -> allAttributes[e.getValue()].add(e.getKey()));
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder(false);
		Stream.of(allAttributes).forEach(list -> builder.add(new SolutionPerson(list)));
		return builder.build();
	}

	private ZebraModel toModel() {
		ZebraModel model = new ZebraModel(chocoSettings);
		for (Entry<AttributeType, Set<Attribute>> entry : puzzle.getAttributeSets().entrySet()) {
			entry.getKey().addToModel(model, entry.getValue(), puzzle.numPeople());
		}
		return model;
	}

}
