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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SolutionPerson {

	private final Map<AttributeType, Attribute> attributes = new LinkedHashMap<>();

	public SolutionPerson(Attribute... attributeList) {
		this(Arrays.asList(attributeList));
	}

	public SolutionPerson(List<Attribute> attributeList) {
		for (Attribute lit : attributeList) {
			attributes.put(lit.type(), lit);
		}
	}

	public List<Attribute> asList() {
		return new ArrayList<>(attributes.values());
	}

	public Set<AttributeType> attributeTypes() {
		return Collections.unmodifiableSet(attributes.keySet());
	}

	public SolutionPerson withAttribute(Attribute attribute) {
		List<Attribute> newAttributes = asList();
		newAttributes.add(attribute);
		return new SolutionPerson(newAttributes);
	}

	public Attribute findAttribute(AttributeType type) {
		return attributes.get(type);
	}

	@Override
	public String toString() {
		return "SolutionPerson [" + asList() + "]";
	}

}
