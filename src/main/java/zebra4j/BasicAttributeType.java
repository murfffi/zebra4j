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
import java.util.List;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Defines an attribute type using a set of labels.
 *
 * <p>
 * This is the easiest way to define a custom attribute. See
 * {@link Attributes#PET} as an example.
 * 
 * @see AllDifferentType
 */
@ToString(exclude = "type")
@EqualsAndHashCode(exclude = "type", callSuper = false)
public class BasicAttributeType extends AllDifferentType {

	private final AttributeType type = this;
	private final List<Attribute> attributes;
	private final String questionSentencePart;

	public BasicAttributeType(Set<String> labels, String questionSentencePart) {
		attributes = new ArrayList<>(labels.size());
		for (String label : labels) {
			attributes.add(new BasicAttribute(attributes.size(), label));
		}
		this.questionSentencePart = questionSentencePart;
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	class BasicAttribute implements Attribute {

		private final int id;
		private final String description;

		@Override
		public String description() {
			return description;
		}

		@Override
		public int asUniqueInt() {
			return id;
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
	public Attribute fromUniqueInt(int input) {
		return attributes.get(input);
	}

	@Override
	public String questionSentencePart() {
		return questionSentencePart;
	}

	@Override
	public List<Attribute> getAttributes(int numPeople) {
		return AllDifferentType.toSolutionSet(attributes.toArray(new Attribute[0]), numPeople);
	}

}
