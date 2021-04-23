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

import zebra4j.Attribute;

abstract class CommutativeFact implements Fact {

	public abstract Attribute getLeft();

	public abstract Attribute getRight();

	@Override
	public boolean equals(Object b) {
		if (!(getClass().isInstance(b))) {
			return false;
		}
		CommutativeFact other = (CommutativeFact) b;
		return getLeft().equals(other.getLeft()) && getRight().equals(other.getRight())
				|| getRight().equals(other.getLeft()) && getLeft().equals(other.getRight());
	}

	@Override
	public int hashCode() {
		return getLeft().hashCode() ^ getRight().hashCode();
	}

	@FunctionalInterface
	interface Source {
		CommutativeFact create(Attribute left, Attribute right);
	}
}
