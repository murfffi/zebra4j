/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 - 2021 Marin Nozhchev
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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.Validate;

import zebra4j.fact.Fact;

/**
 * Builder for {@link Puzzle}
 * 
 * <p>
 * Unlike directly constructing a puzzle, the builder validates if the provided
 * attribute sets and facts are consistent and valid.
 * 
 * @see Demo Demo for example usage
 */
@NotThreadSafe
public class PuzzleBuilder {

	/**
	 * The attribute types and the sub-sets of their values used in the puzzle.
	 */
	private final Map<AttributeType, Set<Attribute>> attributeSets = new LinkedHashMap<>();

	/**
	 * The facts that must be satisfied by any solution of the puzzle under
	 * construction.
	 */
	private final Set<Fact> facts = new LinkedHashSet<>();

	/**
	 * Includes an attribute set of some type in the puzzle
	 * 
	 * @param attributes at least one required, must be from the same type
	 */
	public void addSet(Attribute... attributes) {
		Set<AttributeType> types = Stream.of(attributes).map(Attribute::type).collect(Collectors.toSet());
		Validate.isTrue(types.size() == 1,
				"The attributes must be at least one and from the same type but the types were %s.", types);
		Object result = attributeSets.putIfAbsent(types.iterator().next(), SetUtils.unmodifiableSet(attributes));
		Validate.isTrue(result == null,
				"Multiple sets of the same types are not allowed. Add all values together. Previous set was %s",
				result);
	}

	/**
	 * Creates a new {@link BasicAttributeType} with the given parameters and adds
	 * it to the puzzle under construction.
	 * 
	 * @param typeName        as in
	 *                        {@link BasicAttributeType#BasicAttributeType(Set, String, String)}
	 * @param attributeLabels as in
	 *                        {@link BasicAttributeType#BasicAttributeType(Set, String, String)}
	 * @return the new attribute type
	 */
	public BasicAttributeType addSet(String typeName, String... attributeLabels) {
		BasicAttributeType newType = new BasicAttributeType(new LinkedHashSet<>(Arrays.asList(attributeLabels)), "%s",
				typeName);
		attributeSets.put(newType, new LinkedHashSet<>(newType.getAttributes(attributeLabels.length)));
		return newType;
	}

	/**
	 * Adds a fact to the puzzle under construction.
	 * 
	 * <p>
	 * Add the sets of the attribute that the fact refers to in advance.
	 * 
	 * @param fact required, must apply to the attribute sets already added. See
	 *             {@link Fact#appliesTo(Puzzle)}
	 * @return the builder itself for chaining
	 */
	public PuzzleBuilder addFact(Fact fact) {
		Validate.isTrue(fact.appliesTo(new Puzzle(attributeSets, Collections.emptySet())),
				"The attributes that the fact %s refers must be part of the previously added sets.", fact);
		facts.add(fact);
		return this;
	}

	/**
	 * @return the constructed puzzle
	 */
	public Puzzle build() {
		return new Puzzle(attributeSets, facts);
	}

}
