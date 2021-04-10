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
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Defines an attribute type using a set of labels.
 *
 * <p>
 * This is the easiest way to define a custom attribute. See
 * {@link Attributes#PET} as an example.
 * 
 * <p>
 * This implementation does not support internationalization to remain simple.
 * Just write the labels and patterns in the desired locale.
 * 
 * @see AllDifferentType
 */
@EqualsAndHashCode(exclude = "type", callSuper = false)
public class BasicAttributeType extends AllDifferentType {

	private final AttributeType type = this;
	private final List<Attribute> attributes;
	private final String questionSentencePart;
	private final String typeName;

	public BasicAttributeType(Set<String> labels, String questionSentencePart, String typeName) {
		attributes = new ArrayList<>(labels.size());
		for (String label : labels) {
			attributes.add(new BasicAttribute(attributes.size(), label));
		}
		// The message below is passed to String.format hence % is escaped.
		Validate.isTrue(questionSentencePart.contains("%s"),
				"questionSentencePart should contain a %%s placeholder for the person the question is about.");
		this.questionSentencePart = questionSentencePart;
		this.typeName = typeName;
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	class BasicAttribute implements Attribute {

		private final int id;
		private final String description;

		@Override
		public String description(Locale locale) {
			return description;
		}

		@Override
		public AttributeType type() {
			return type;
		}

		@Override
		public String toString() {
			return description;
		}
	}

	@Override
	public String questionSentencePart(Locale locale) {
		return questionSentencePart;
	}

	@Override
	public List<Attribute> getAttributes(int numPeople) {
		return AllDifferentType.toSolutionSet(attributes.toArray(new Attribute[0]), numPeople);
	}

	@Override
	public String toString() {
		return typeName;
	}

}
