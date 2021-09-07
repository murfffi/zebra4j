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
package zebra4j.util;

import java.util.List;

/**
 * Data randomization, as used in zebra4j
 * 
 * <p>
 * This interface allows implementing the data randomization using different
 * libraries. The built-in implementation is {@link JDKRandom}, which uses
 * randomization from the JDK. Users of the library may prefer commons-rng, as
 * in <a href=
 * 'https://github.com/murfffi/zebra-apps/blob/main/zebrajs/src/main/java/zebra4j/apps/SeededRandom.java'>this
 * case</a>.
 * 
 * <p>
 * Some unit tests use a no-op implementation of this interface to be
 * predictable.
 */
public interface Randomness {

	/**
	 * Shuffles the provided list in place
	 * 
	 * @param list, required
	 */
	void shuffle(List<?> list);

	/**
	 * Same as {@link java.util.Random#nextInt(int)}
	 */
	int nextInt(int bound);

}
