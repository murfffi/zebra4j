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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import zebra4j.AtHouse;
import zebra4j.Clothes;
import zebra4j.Criminal;
import zebra4j.LocalizationTestUtils;
import zebra4j.PersonName;
import zebra4j.Puzzle;
import zebra4j.PuzzleBuilder;
import zebra4j.fact.CommutativeFact.Source;

public class NearbyHouseTest {

	@Test
	public void testGenerate() {
		BothTrueTest.testGenerate(NearbyHouse.TYPE);
	}

	@Test
	public void testPostTo() {
		BothTrueTest.testPostTo(NearbyHouse.TYPE);
	}

	@Test
	public void testDescribe() {
		Fact f = new NearbyHouse(1, PersonName.GEORGE, PersonName.ELENA);
		LocalizationTestUtils.testDescribe(f::describe);
	}

	@Test
	public void testEquals_Attributes() {
		CommutativeFactUtils.testEquals((l, r) -> new NearbyHouse(2, l, r));
	}

	@Test
	public void testHashcode_Attributes() {
		CommutativeFactUtils.testHashcode((l, r) -> new NearbyHouse(2, l, r));
	}

	@Test
	public void testEquals_Distance() {
		assertNotEquals(new NearbyHouse(1, Clothes.GREEN, PersonName.ELENA),
				new NearbyHouse(2, Clothes.GREEN, PersonName.ELENA));
	}

	@Test
	public void testHashcode_Distance() {
		assertNotEquals(new NearbyHouse(1, Clothes.GREEN, PersonName.ELENA).hashCode(),
				new NearbyHouse(2, Clothes.GREEN, PersonName.ELENA).hashCode());
	}

	@Test
	public void testAppliesToPuzzle() {
		Source factSource = (l, r) -> new NearbyHouse(2, l, r);
		PuzzleBuilder builder = new PuzzleBuilder();
		builder.addSet(Clothes.BLUE, Clothes.GREEN);
		builder.addSet(PersonName.ELENA, PersonName.THEODORA);
		builder.addSet(new AtHouse(1), new AtHouse(2));
		Puzzle puzzle = builder.build();
		assertTrue(factSource.create(Clothes.BLUE, PersonName.ELENA).appliesTo(puzzle));
		assertFalse(factSource.create(Clothes.YELLOW, PersonName.ELENA).appliesTo(puzzle));
		assertFalse(factSource.create(Criminal.YES, PersonName.ELENA).appliesTo(puzzle));
	}

}
