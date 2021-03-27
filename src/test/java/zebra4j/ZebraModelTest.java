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

import org.chocosolver.solver.DefaultSettings;
import org.chocosolver.solver.variables.IntVar;
import org.junit.Assert;
import org.junit.Test;

public class ZebraModelTest {

	@Test
	public void testToOptionalAttribute() {
		ZebraModel model = new ZebraModel(new DefaultSettings());
		IntVar x = model.createUniqueVariable(PersonName.GEORGE, 4);
		Assert.assertEquals(PersonName.GEORGE, model.toOptionalAttribute(x).get());
	}

}
