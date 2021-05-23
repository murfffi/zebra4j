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
package zebra4j.fact;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import zebra4j.Attribute;
import zebra4j.Criminal;
import zebra4j.PuzzleSolution;
import zebra4j.SolutionPerson;
import zebra4j.ZebraModel;

@AllArgsConstructor
@ToString
@Getter
@Slf4j
@Immutable
public class Different extends CommutativeFact {

	public static Fact.Type TYPE = new Fact.Type() {

		@Override
		public List<Fact> generate(PuzzleSolution solution) {
			List<Fact> result = new ArrayList<>();
			for (SolutionPerson person : solution.getPeople()) {
				List<Attribute> attributes = person.asList();
				for (int i = 0; i < attributes.size(); ++i) {
					for (int j = i + 1; j < attributes.size(); ++j) {
						Attribute other = attributes.get(j);
						for (Attribute different : solution.getAttributeSets().get(other.type())) {
							if (!Criminal.NO.equals(different) && !different.equals(other)) {
								result.add(new Different(attributes.get(i), different));
							}
						}
					}
				}
			}
			return result;
		}

		@Override
		public String toString() {
			return Different.class.getName();
		}
	};

	private final Attribute left;
	private final Attribute right;

	@Override
	public void postTo(ZebraModel model) {
		// The person of literal1 is the same as the person of literal2
		IntVar leftVar = model.getVariableFor(left);
		IntVar rightVar = model.getVariableFor(right);

		if (leftVar != null && rightVar != null) {
			Constraint constraint = model.getChocoModel().arithm(leftVar, "!=", rightVar);
			log.trace("Adding choco contraint: {}", constraint);
			constraint.post();
		}
	}

	@Override
	public boolean appliesTo(PuzzleSolution solution) {
		return solution.findPerson(left).filter(person -> !person.hasAttribute(right)).isPresent();
	}
}
