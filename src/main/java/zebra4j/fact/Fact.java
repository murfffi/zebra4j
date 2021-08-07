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

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import zebra4j.AttributeType;
import zebra4j.BasicPuzzle;
import zebra4j.PuzzleSolution;
import zebra4j.ZebraModel;

/**
 * A fact given as a clue to the players
 *
 * <p>
 * Facts are used as members of sets so they must implement equals and hashCode
 * correctly.
 * 
 * <p>
 * Facts are typically relationships between attributes or the people that have
 * them. When implementing a fact about two attributes, consider extending
 * {@link CommutativeFact}.
 */
public interface Fact {

	/**
	 * A type of facts
	 * 
	 * <p>
	 * Implementations of this interface are used as members of sets so they must
	 * implement equals and hashCode correctly. Singleton implementations are fine
	 * because {@link Object#equals(Object)} and {@link Object#hashCode()} work
	 * correctly for singletons.
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

	/**
	 * @param puzzle required
	 * @return if the fact is relevant to the given puzzle e.g. if the fact is a
	 *         relationship between attributes and the puzzle contains all of them
	 */
	boolean appliesTo(BasicPuzzle puzzle);

	/**
	 * Describes the facts in natural language in the given locale
	 * 
	 * @param locale required
	 * @return a natural language sentence
	 */
	String describe(Locale locale);

	/**
	 * Collect the types of the attributes referenced by the fact
	 * 
	 * @return a non-empty list if the fact is a relationship between attributes,
	 *         empty otherwise
	 */
	Collection<AttributeType> attributeTypes();

}
