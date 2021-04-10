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

import java.util.Set;

import org.apache.commons.collections4.SetUtils;

/**
 * A library of people attributes, demonstrating different ways to define them.
 */
public interface Attributes {

	/**
	 * Pet attribute. Serves as an example how to define attributes with
	 * {@link BasicAttributeType}
	 */
	public static final AttributeType PET = new BasicAttributeType(SetUtils.unmodifiableSet(//
			"with a zebra", //
			"with a cat", //
			"with a dog", //
			"with a hamster", //
			"with a snake"), //
			"What pet are %s with?", "Pet");

	/**
	 * Clothes attribute. Serves as an example how to define attributes as an enum.
	 * 
	 * <p>
	 * Defining attributes as an enum allows for value-dependent implementation of
	 * methods like {@link AttributeType#questionSentencePart} or
	 * {@link Attribute#description} .
	 */
	public static final AttributeType CLOTHES = Clothes.TYPE;
	public static final AttributeType AT_HOUSE = AtHouse.TYPE;
	public static final AttributeType NAME = PersonName.TYPE;

	public static final AttributeType CRIMINAL = Criminal.TYPE;

	/**
	 * The set of attributes used by the demos
	 */
	public static final Set<AttributeType> DEFAULT_TYPES = SetUtils.unmodifiableSet(NAME, AT_HOUSE, CLOTHES, CRIMINAL,
			PET);
}
