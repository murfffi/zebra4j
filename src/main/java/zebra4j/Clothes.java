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

public enum Clothes implements Attribute {

	RED, BLUE, GREEN, YELLOW;

	public static AttributeType TYPE = new AllDifferentType() {

		@Override
		public String questionSentencePart(Locale locale) {
			return Localization.translate(Clothes.class, "questionSentencePart", locale);
		}

		@Override
		public List<Attribute> getAttributes(int numPeople) {
			return toSolutionSet(Clothes.values(), numPeople);
		}

		@Override
		public String toString() {
			return Clothes.class.getName();
		}

	};

	@Override
	public String description(Locale locale) {
		String name = Localization.translateEnum(this, locale).toLowerCase(locale);
		String pattern = Localization.translate(getClass(), "pattern", locale);
		return String.format(pattern, name);
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
