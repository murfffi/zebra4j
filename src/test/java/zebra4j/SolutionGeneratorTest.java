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

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

import zebra4j.util.JDKRandom;

public class SolutionGeneratorTest {

	@Test
	public void testGenerate_Stable() {
		SolutionGenerator sol1 = new SolutionGenerator(Attributes.DEFAULT_TYPES, 3, new JDKRandom(1));
		SolutionGenerator sol2 = new SolutionGenerator(new HashSet<>(Attributes.DEFAULT_TYPES), 3, new JDKRandom(1));
		assertEquals(sol1.generate(), sol2.generate());
	}

}
