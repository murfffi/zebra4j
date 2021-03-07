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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;

public class ClothesTest {

	@Test
	public void testDescription() {
		LocalizationTestUtils.testDescribe(Clothes.RED::description);
	}

	@Test
	public void testType_DescribeSet() throws Exception {
		Set<Attribute> attrSet = new HashSet<>(Arrays.asList(Clothes.values()));
		LocalizationTestUtils.testDescribe(l -> Clothes.TYPE.describeSet(attrSet, l));
	}

	@Test
	public void testType_DescribeSetAllRepresented() throws Exception {
		Set<Attribute> attrSet = new HashSet<>(Arrays.asList(Clothes.values()));
		for (Locale l : LocalizationTestUtils.SUPPORTED_LOCALES) {
			String description = Clothes.TYPE.describeSet(attrSet, l);
			attrSet.forEach(attr -> assertTrue(description.contains(attr.description(l))));
		}
	}
}
