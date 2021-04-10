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
package zebra4j.fact;

import java.util.Locale;

import zebra4j.Attribute;
import zebra4j.Localization;
import zebra4j.PersonName;

class FactUtil {

	static String describe(Class<?> cls, Locale locale, Attribute left, Attribute right) {
		String patternId = "genericPattern";
		if (left instanceof PersonName) {
			patternId = "namePattern";
		}
		String pattern = Localization.translate(cls, patternId, locale);
		return String.format(pattern, left.description(locale), right.description(locale));
	}

}
