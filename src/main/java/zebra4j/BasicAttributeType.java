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
import java.util.Optional;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.Validate;

import com.google.gson.Gson;

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
@EqualsAndHashCode(callSuper = false)
@Immutable
public class BasicAttributeType extends AllDifferentType {

	public static BasicAttributeType fromJson(String json) {
		return new Gson().fromJson(json, BasicAttributeType.class);
	}

	private final List<Attribute> attributes;
	private final String questionPattern;
	private final String typeName;

	/**
	 * Create a new basic type of attribute with enumerated set of attributes
	 * 
	 * @param labels          the labels of all attributes of this type, starting
	 *                        with a verb e.g. "is from France", "owns a dog". See
	 *                        also {@link Attribute#description}
	 * @param questionPattern See {@link AttributeType#questionSentencePart}
	 * @param typeName        Short name of the type, used in debug output
	 */
	public BasicAttributeType(Set<String> labels, String questionPattern, String typeName) {
		attributes = new ArrayList<>(labels.size());
		for (String label : labels) {
			attributes.add(new BasicAttribute(label, this));
		}
		// The message below is passed to String.format hence % is escaped.
		Validate.isTrue(questionPattern.contains("%s"),
				"questionPattern should contain a %%s placeholder for the person the question is about.");
		this.questionPattern = questionPattern;
		this.typeName = typeName;
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode(exclude = "type")
	static class BasicAttribute implements Attribute {

		private final String label;
		private transient final AttributeType type;

		@Override
		public String description(Locale locale) {
			return label;
		}

		@Override
		public AttributeType type() {
			return type;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	@Override
	public String questionSentencePart(Locale locale) {
		return questionPattern;
	}

	@Override
	public List<Attribute> getAttributes(int numPeople) {
		return AllDifferentType.toSolutionSet(attributes.toArray(new Attribute[0]), numPeople);
	}

	@Override
	public String toString() {
		return typeName;
	}

	/**
	 * Finds the attribute with the given label if any, case sensitive
	 * 
	 * @param label required, case sensitive
	 */
	public Optional<Attribute> findByLabel(String label) {
		return attributes.stream().filter(a -> ((BasicAttribute) a).label.equals(label)).findAny();
	}

}
