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

import zebra4j.util.Localization;

/**
 * The name attribute of a person
 * 
 * <p>
 * Most facts have special desription sentence patterns when one of the member
 * attributes is {@link PersonName}.
 */
public enum PersonName implements Attribute {

	IVAN, ELENA, PETER, THEODORA, GEORGE;

	public static AttributeType TYPE = new AllDifferentType() {

		@Override
		public String questionSentencePart(Locale locale) {
			return Localization.translate(PersonName.class, "questionSentencePart", locale);
		}

		@Override
		public List<Attribute> getAttributes(int numPeople) {
			return toSolutionSet(PersonName.values(), numPeople);
		}

		@Override
		public String describeSet(java.util.Collection<Attribute> set, Locale locale) {
			String[] descriptions = set.stream().map(a -> a.description(locale)).toArray(String[]::new);
			return Localization.translate(PersonName.class, "anyPersonIs", locale) + " "
					+ String.join(", ", descriptions) + ".";
		};

		@Override
		public String toString() {
			return PersonName.class.getName();
		}

	};

	@Override
	public String description(Locale locale) {
		return Localization.translateEnum(this, locale);
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
