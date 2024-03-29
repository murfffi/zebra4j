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
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import lombok.EqualsAndHashCode;

/**
 * A specification of the attributes of a person in a solution of a
 * {@link BasicPuzzle}
 */
@EqualsAndHashCode
@Immutable
public class SolutionPerson {

	private final Map<AttributeType, Attribute> attributes = new LinkedHashMap<>();

	/**
	 * Defines a person from their attributes
	 * 
	 * @param attributeList all attributes of the person, required
	 */
	public SolutionPerson(Attribute... attributeList) {
		this(Arrays.asList(attributeList));
	}

	/**
	 * Defines a person from their attributes
	 * 
	 * @param attributeList all attributes of the person, required
	 */
	public SolutionPerson(List<Attribute> attributeList) {
		for (Attribute lit : attributeList) {
			attributes.put(lit.type(), lit);
		}
	}

	/**
	 * @return a copy of the attributes as a list
	 */
	public List<Attribute> asList() {
		return new ArrayList<>(attributes.values());
	}

	/**
	 * @return a unmodifiable view of the types of the attributes
	 */
	public Set<AttributeType> attributeTypes() {
		return Collections.unmodifiableSet(attributes.keySet());
	}

	/**
	 * @param attribute required
	 * @return a copy of this person with the given attribute added
	 */
	public SolutionPerson withAttribute(Attribute attribute) {
		List<Attribute> newAttributes = asList();
		newAttributes.add(attribute);
		return new SolutionPerson(newAttributes);
	}

	/**
	 * Finds one attributes of the person given type
	 * 
	 * @param type
	 * @return the attribute if it exists, null otherwise
	 */
	public Attribute findAttribute(AttributeType type) {
		return attributes.get(type);
	}

	/**
	 * @param attr required
	 * @return if the person has the given attribute
	 */
	public boolean hasAttribute(Attribute attr) {
		return attributes.values().contains(attr);
	}

	/**
	 * Describe the person-in-solution in natural language
	 * 
	 * @param locale the locale of the natural language
	 * @return an array of attribute descriptions
	 */
	public String[] describe(Locale locale) {
		return attributes.values().stream().map(attr -> attr.description(locale)).toArray(String[]::new);
	}

	@Override
	public String toString() {
		return "SolutionPerson [" + asList() + "]";
	}

}
