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

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.chocosolver.solver.ChocoSettings;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import lombok.Getter;

public class ZebraModel {

	@Getter
	private final Model chocoModel = new Model(UUID.randomUUID().toString(), new ChocoSettings());

	private final BidiMap<Attribute, IntVar> uniqueAttributeVariables = new DualHashBidiMap<Attribute, IntVar>();

	public IntVar createUniqueVariable(Attribute attr, int numPeople) {
		IntVar var = chocoModel.intVar(varName(attr), 0, numPeople - 1);
		uniqueAttributeVariables.put(attr, var);
		return var;
	}

	public IntVar getVariableFor(Attribute uniqueAttribute) {
		return uniqueAttributeVariables.get(uniqueAttribute);
	}

	public String varName(Attribute attr) {
		return String.format("person_of_'%s'_'%s'", getTypeId(attr), attr.asUniqueInt());
	}

	private String getTypeId(Attribute attr) {
		AttributeType type = attr.type();
		return type.getClass().getSimpleName() + type.hashCode();
	}

	public Optional<Attribute> toOptionalAttribute(IntVar var) {
		return Optional.ofNullable(uniqueAttributeVariables.getKey(var));
	}
}
