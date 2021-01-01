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
package zebra4j.fact;

import java.util.List;

import zebra4j.PuzzleSolution;
import zebra4j.ZebraModel;

/**
 * A fact given as clus to the players
 *
 * <p>
 * Facts are used as members of sets so they must implement equals and hashCode
 * correctly.
 */
public interface Fact {

	/**
	 * A type of facts
	 * 
	 * <p>
	 * Implementations of this interface are used as members of sets so they must
	 * implement equals and hashCode correctly.
	 */
	interface Type {

		/**
		 * Generates all facts of that type that apply to the given solution
		 * 
		 * @param solution required, not empty
		 * @return a list of facts, empty if the type of facts does not apply to the
		 *         attribute types in the solution
		 */
		List<Fact> generate(PuzzleSolution solution);
	}

	/**
	 * @param model
	 */
	void postTo(ZebraModel model);

	/**
	 * @param solution required
	 * @return if the fact is true about the people in the given solution
	 */
	boolean appliesTo(PuzzleSolution solution);
}
