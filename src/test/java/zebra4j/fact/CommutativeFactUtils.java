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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import zebra4j.Clothes;
import zebra4j.PersonName;
import zebra4j.fact.CommutativeFact.Source;

public interface CommutativeFactUtils {

	public static void testEquals(Source factSource) {
		assertEquals(factSource.create(Clothes.GREEN, PersonName.ELENA),
				factSource.create(PersonName.ELENA, Clothes.GREEN));
		assertNotEquals(factSource.create(Clothes.GREEN, PersonName.ELENA), null);
	}

	public static void testHashcode(Source factSource) {
		assertEquals(factSource.create(Clothes.GREEN, PersonName.ELENA).hashCode(),
				factSource.create(PersonName.ELENA, Clothes.GREEN).hashCode());
	}

}
