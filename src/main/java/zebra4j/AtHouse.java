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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Value;

@Value
public class AtHouse implements Attribute {

	public static AttributeType TYPE = new AllDifferentType() {

		@Override
		public String questionSentencePart(Locale locale) {
			return Localization.translate(AtHouse.class, "questionSentencePart", locale);
		}

		@Override
		public List<Attribute> getAttributes(int numPeople) {
			return IntStream.rangeClosed(1, numPeople).mapToObj(AtHouse::new).collect(Collectors.toList());
		}

		@Override
		public String describeSet(Set<Attribute> set, Locale locale) {
			String pattern = Localization.translate(AtHouse.class, "pattern", locale);
			return String.format(pattern, set.size());
		};

		@Override
		public String toString() {
			return AtHouse.class.getName();
		}

	};

	/**
	 * The number of the position of the house from left to right, starting at 1.
	 */
	private final int position;

	@Override
	public String description(Locale locale) {
		return Localization.translate(getClass(), "description", locale) + position;
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
