/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 - 2021 Marin Nozhchev
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.Character.UnicodeBlock;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.LocaleUtils;

public class LocalizationTestUtils {

	public static final Locale BULGARIAN = LocaleUtils.toLocale("bg");

	public static final Collection<Locale> SUPPORTED_LOCALES = Arrays.asList(Locale.ENGLISH, BULGARIAN);

	public static void testDescribe(Function<Locale, String> describe) {
		SUPPORTED_LOCALES.stream().forEach(l -> testDescribe(describe, l));
		long uniqueTranslations = SUPPORTED_LOCALES.stream().map(describe).distinct().count();
		assertEquals(SUPPORTED_LOCALES.size(), uniqueTranslations);
	}

	public static void testDescribe(Function<Locale, String> describe, Locale locale) {
		String output = describe.apply(locale);
		testLocale(output, locale);
	}

	public static void testLocale(String output, Locale locale) {
		Set<UnicodeBlock> expected = new HashSet<>();
		expected.add(UnicodeBlock.BASIC_LATIN);
		if (BULGARIAN.equals(locale)) {
			expected.add(UnicodeBlock.CYRILLIC);
		}
		Set<UnicodeBlock> found = new HashSet<>();
		for (char c : output.toCharArray()) {
			UnicodeBlock block = UnicodeBlock.of(c);
			assertTrue(String.format("Characted [%s] is in %s, not in %s. Full string: %s", c, block, expected, output),
					expected.contains(block));
			found.add(block);
		}
		assertEquals(expected, found);

	}

}
