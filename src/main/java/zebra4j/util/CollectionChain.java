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

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;

import lombok.AllArgsConstructor;

/**
 * Immutable ordered union view on two collections
 * 
 * <p>
 * Like {@link IterableUtils#chainedIterable} but for collections. We don't
 * extend from {@link java.util.AbstractCollection} to avoid inheriting
 * inefficient operations.
 * 
 * <p>
 * While no modifications can be made via the view, any changes to the
 * underlying collections will be seen through the view.
 *
 * @param <E> the collection element type
 */
@AllArgsConstructor
public class CollectionChain<E> implements Collection<E> {

	private final Collection<E> first;
	private final Collection<E> second;

	@Override
	public Iterator<E> iterator() {
		return IteratorUtils.chainedIterator(first.iterator(), second.iterator());
	}

	@Override
	public int size() {
		return first.size() + second.size();
	}

	@Override
	public boolean isEmpty() {
		return first.isEmpty() && second.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return first.contains(o) || second.contains(o);
	}

	@Override
	public Object[] toArray() {
		return IteratorUtils.toArray(iterator());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		return (T[]) toArray();
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("immutable view");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("immutable view");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(this::contains);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("immutable view");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("immutable view");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("immutable view");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("immutable view");
	}

}
