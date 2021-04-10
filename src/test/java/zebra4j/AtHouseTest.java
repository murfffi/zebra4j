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
package zebra4j;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class AtHouseTest {

	@Test
	public void testQuestionSentencePart() {
		LocalizationTestUtils.testDescribe(AtHouse.TYPE::questionSentencePart);
	}

	@Test
	public void testType_DescribeSet() throws Exception {
		Set<Attribute> attrSet = new HashSet<>(AtHouse.TYPE.getAttributes(3));
		LocalizationTestUtils.testDescribe(l -> AtHouse.TYPE.describeSet(attrSet, l));
	}

}
