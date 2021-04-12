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
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Subsequence<E> extends AbstractCollection<E> {

	private final List<E> wrapped;
	private final BitSet activeIdx;

	public Subsequence(List<E> wrapped) {
		this.wrapped = wrapped;
		this.activeIdx = new BitSet(wrapped.size());
		this.activeIdx.set(0, wrapped.size());
	}

	@Override
	public Iterator<E> iterator() {
		// Iterator<Integer> iter = activeIdx.stream().iterator();
		return new Iterator<E>() {
			int next = activeIdx.nextSetBit(0);

			@Override
			public boolean hasNext() {
				return next != -1; // iter.hasNext();
			}

			@Override
			public E next() {
				if (next != -1) {
					int ret = next;
					next = activeIdx.nextSetBit(next + 1);
					return wrapped.get(ret);
				} else {
					throw new NoSuchElementException();
				}
				// return wrapped.get(iter.next());
			}
		};
	}

	public void unblock(int idx) {
		activeIdx.set(idx, true);
	}

	public void block(int idx) {
		activeIdx.set(idx, false);
	}

	@Override
	public int size() {
		return activeIdx.cardinality();
	}

}
