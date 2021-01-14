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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

/**
 * A type of attribute that is guaranteed to be different for all people
 */
public abstract class AllDifferentType implements AttributeType {

	@Override
	public void addToModel(ZebraModel zebraModel, Set<Attribute> attributesOfType, int numPeople) {
		Model model = zebraModel.getChocoModel();
		List<IntVar> varsForType = new ArrayList<>();
		for (Attribute attr : attributesOfType) {
			IntVar var = zebraModel.createUniqueVariable(attr, numPeople);
			varsForType.add(var);
		}
		// The person for each attribute of a type is different.
		model.allDifferent(varsForType.toArray(new IntVar[0])).post();
	}

	/**
	 * Returns a list of the first "numPeople" elements of the given array
	 * 
	 * <p>
	 * Used to implement {@link #getAttributes} in {@link AllDifferentType}
	 * implementations with an fixed set of attributes.
	 * 
	 * @param allValues
	 * @param numPeople
	 * @return a list of attributes as in {@link #getAttributes}
	 */
	protected static List<Attribute> toSolutionSet(Attribute[] allValues, int numPeople) {
		if (numPeople > allValues.length) {
			throw new IllegalArgumentException(
					String.format("%s has less elements - %s - then number of people requested: %s",
							allValues.getClass().getComponentType(), allValues.length, numPeople));
		}
		return Arrays.asList(Arrays.copyOfRange(allValues, 0, numPeople));
	}
}
