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
package zebra4j.fact;

import org.junit.Test;

public class DifferentTest {

	@Test
	public void testGenerate() {
		BothTrueTest.testGenerate(Different.TYPE);
	}

	@Test
	public void testPostTo() {
		BothTrueTest.testPostTo(Different.TYPE);
	}

	@Test
	public void testEquals() {
		CommutativeFactUtils.testEquals(Different::new);
	}

	@Test
	public void testHashcode() {
		CommutativeFactUtils.testHashcode(Different::new);
	}

	@Test
	public void testAppliesToPuzzle() {
		CommutativeFactUtils.testAppliesToPuzzle(Different::new);
	}
}
