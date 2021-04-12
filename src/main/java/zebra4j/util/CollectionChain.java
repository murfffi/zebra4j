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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections4.IteratorUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CollectionChain<E> extends AbstractCollection<E> {

	private final Collection<E> first, second;

	@Override
	public Iterator<E> iterator() {
		return IteratorUtils.chainedIterator(first.iterator(), second.iterator());
	}

	@Override
	public int size() {
		return first.size() + second.size();
	}

}
