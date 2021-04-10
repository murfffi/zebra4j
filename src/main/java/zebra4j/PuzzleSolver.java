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
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	 * @param zebraModel
	 * @return a list of choco-solver solutions; useful to count solutions
	 */
	List<Solution> solveChoco(ZebraModel zebraModel) {
		for (Fact fact : puzzle.getFacts()) {
			fact.postTo(zebraModel);
		}
		Model model = zebraModel.getChocoModel();
		List<Solution> solutions = new ArrayList<>();
		Solver solver = model.getSolver();
		while (solver.solve()) {
			Solution solution = new Solution(model).record();
			if (!retrieveVars(solution).isEmpty()) {
				solutions.add(solution);
			}
		}
		return solutions;
	}

	public List<PuzzleSolution> solve() {
		log.debug("Solving puzzle {}", puzzle);
		final ZebraModel zebraModel = toModel();
		List<Solution> solutions = solveChoco(zebraModel);
		List<PuzzleSolution> zebraSolutions = solutions.stream().map(choco -> fromChocoSolution(choco, zebraModel))
				.distinct().collect(Collectors.toList());
		log.trace("Found {} distinct solutions", zebraSolutions.size());
		return zebraSolutions;
	}

	private PuzzleSolution fromChocoSolution(Solution choco, ZebraModel model) {
		int numPeople = puzzle.numPeople();
		@SuppressWarnings("unchecked")
		List<Attribute>[] allAttributes = new List[numPeople];
		for (int i = 0; i < allAttributes.length; ++i) {
			allAttributes[i] = new ArrayList<>();
		}
		for (IntVar var : retrieveVars(choco)) {
			int person = choco.getIntVal(var);
			Optional<Attribute> attribute = model.toOptionalAttribute(var);
			log.trace("Var {} was mapped to attribute {}", var.getName(), attribute);
			attribute.ifPresent(attr -> allAttributes[person].add(attr));
		}
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder(false);
		Stream.of(allAttributes).forEach(list -> builder.add(new SolutionPerson(list)));
		return builder.build();
	}

	private List<IntVar> retrieveVars(Solution choco) {
		// If people are two, all vars become boolean.
		return choco.retrieveIntVars(true);
	}

	private ZebraModel toModel() {
		ZebraModel model = new ZebraModel(chocoSettings);
		for (Entry<AttributeType, Set<Attribute>> entry : puzzle.getAttributeSets().entrySet()) {
			entry.getKey().addToModel(model, entry.getValue(), puzzle.numPeople());
		}
		return model;
	}

}
