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
package zebra4j.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.text.CaseUtils;

/**
 * Localization utilities for zebra4j
 */
public interface Localization {

	/**
	 * Translates the attribute, identified by the key, belonging to the given class
	 * 
	 * @param cls    the class, required
	 * @param key    the attribute key, required
	 * @param locale the target locale, required
	 * @return text or label in the given locale
	 * 
	 * @throws MissingResourceException if the locale is not supported
	 */
	public static String translate(Class<?> cls, String key, Locale locale) {
		String bundleName = cls.getName().replace("zebra4j.", "zebra4j.bundle.");
		return ResourceBundle.getBundle(bundleName, locale).getString(key);
	}

	/**
	 * Translates an enum value, falling back to value name if the locale is not
	 * supported
	 * 
	 * @param value  required
	 * @param locale the target locale, required
	 * @return text or label in the given locale or the enum name
	 */
	public static String translateEnum(Enum<?> value, Locale locale) {
		try {
			return translate(value.getClass(), value.name(), locale);
		} catch (MissingResourceException e) {
			return CaseUtils.toCamelCase(value.name(), true);
		}
	}

}
