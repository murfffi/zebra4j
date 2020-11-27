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

/**
 * A known attribute for a person like a specific "name", "pet" or if the person
 * is criminal
 * 
 * @see Attributes
 */
public interface Attribute {

	/**
	 * As in the sentence "Ivan is {description}"
	 */
	String description();

	/**
	 * @return an integer that uniquely identifies the attribute within its type
	 */
	int asUniqueInt();

	/**
	 * @return the type of attribute, not null
	 */
	AttributeType type();
}
