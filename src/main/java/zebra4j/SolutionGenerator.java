/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 Marin Nozhchev
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

import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.collections4.SetUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SolutionGenerator implements Supplier<PuzzleSolution> {

	public static final Set<AttributeType> DEFAULT_TYPES = SetUtils.unmodifiableSet(AtHouse.TYPE, Clothes.TYPE,
			PersonName.TYPE, Criminal.TYPE);

	private final Set<AttributeType> attributeTypes;
	private final Random rnd;

	@Override
	public PuzzleSolution get() {
		// TODO Auto-generated method stub
		return null;
	}

	public PuzzleSolution generate() {
		return get();
	}
}
