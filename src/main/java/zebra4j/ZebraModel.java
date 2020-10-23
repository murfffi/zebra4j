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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import lombok.Getter;

public class ZebraModel {

	private static final Pattern VAR_REGEX = Pattern.compile("person_of_'(\\w+)'_'([0-9]+)'");

	@Getter
	private final Model chocoModel = new Model();

	private final Map<String, AttributeType> typeMap = new HashMap<>();

	private Map<Attribute, IntVar> uniqueAttributeVariables = new HashMap<>();

	public void addUniqueVariable(Attribute attr, IntVar var) {
		uniqueAttributeVariables.put(attr, var);
	}

	public IntVar getVariableFor(Attribute uniqueAttribute) {
		return uniqueAttributeVariables.get(uniqueAttribute);
	}

	public String varName(Attribute attr) {
		typeMap.putIfAbsent(attr.typeName(), attr.type());
		return String.format("person_of_'%s'_'%s'", attr.typeName(), attr.asUniqueInt());
	}

	public Attribute toAttribute(String name) {
		Matcher m = VAR_REGEX.matcher(name);
		m.matches();
		int attributeId = Integer.parseInt(m.group(2));
		AttributeType attrType = typeMap.get(m.group(1));
		return attrType.fromUniqueInt(attributeId);
	}
}
