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

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * A type attribute for a person like a "name", "pet" or if the person is
 * criminal
 * 
 * @see Attribute
 */
public interface AttributeType {

	/**
	 * Adds rules related to the subset of attributes of this type used in a puzzle
	 * to the model describing the puzzle
	 * 
	 * @param model
	 * @param attributesOfType
	 * @param numPeople
	 */
	void addToModel(ZebraModel model, Set<Attribute> attributesOfType, int numPeople);

	/**
	 * Returns the pattern for questions "about" this attribute type
	 * 
	 * @param locale
	 * @return a pattern for {@link String#format} with a single placeholder for the
	 *         attribute of the person the question is towards.
	 * 
	 * @see Question
	 */
	String questionSentencePart(Locale locale);

	/**
	 * Return some attributes of this type.
	 * 
	 * <p>
	 * Used when generating solutions.
	 * 
	 * @param count how many attributes to return
	 * @return A subset of the attributes of this type
	 * @throws IllegalArgumentException if count is larger than the number of all
	 *                                  possible attributes of this type
	 */
	List<Attribute> getAttributes(int count);

	/**
	 * Describe the given set of attributes in the form of sentence, in the given
	 * locale, describing a rule for solving a puzzle.
	 * 
	 * <p>
	 * The sentence is in the form: People's names are Mary, Jane and Steve.
	 * 
	 * @param subset the set of attributes of this type, used in a puzzle
	 * @param locale
	 * @return a sentence string, not null.
	 */
	String describeSet(Set<Attribute> subset, Locale locale);
}
