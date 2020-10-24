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

import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

public interface Fact {

	@Value
	@Slf4j
	public class BothTrue implements Fact {
		private final Attribute left, right;

		@Override
		public String toString() {
			if (left instanceof PersonName) {
				return String.format("%s e %s", left.description(), right.description());
			}
			return String.format("Този който е %s, e и %s.", left.description(), right.description());
		}

		@Override
		public void postTo(ZebraModel model) {
			// The person of literal1 is the same as the person of literal2
			IntVar leftVar = model.getVariableFor(left);
			IntVar rightVar = model.getVariableFor(right);

			if (leftVar != null && rightVar != null) {
				Constraint constraint = model.getChocoModel().arithm(leftVar, "=", rightVar);
				log.trace("Adding choco contraint: {}", constraint);
				constraint.post();
			}
		}
	}

	@Value
	@Slf4j
	public class Different implements Fact {
		private final Attribute left, right;

		@Override
		public String toString() {
			if (left instanceof PersonName) {
				return String.format("%s не e %s", left.description(), right.description());
			}
			return String.format("Този който е %s, не е %s.", left.description(), right.description());
		}

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
	}

	public void postTo(ZebraModel model);
}
