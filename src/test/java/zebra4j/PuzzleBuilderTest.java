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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zebra4j.fact.BothTrue;

public class PuzzleBuilderTest {

	@Test
	public void testAddSetAttributeArray_ValidateSameType() {
		PuzzleBuilder builder = new PuzzleBuilder();
		assertThrows(IllegalArgumentException.class, () -> builder.addSet(Clothes.BLUE, PersonName.ELENA));
	}

	@Test
	public void testAddSetAttributeArray_ValidateAppliesTo() {
		PuzzleBuilder builder = new PuzzleBuilder();
		assertThrows(IllegalArgumentException.class,
				() -> builder.addFact(new BothTrue(Clothes.BLUE, PersonName.ELENA)));
	}

	@Test
	public void testAddSetAttributeArray() {
		PuzzleBuilder builder = new PuzzleBuilder();
		builder.addSet(Clothes.BLUE);
		assertTrue(builder.build().contains(Clothes.BLUE));
	}
}
