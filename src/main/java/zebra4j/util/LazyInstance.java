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

import java.util.function.Supplier;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * {@link LazyInitializer} implementation based on {@link Supplier}
 *
 * @param <T> the type of results supplied
 */
@RequiredArgsConstructor
public class LazyInstance<T> extends LazyInitializer<T> implements Supplier<T> {

	private final Supplier<T> supplier;

	@Override
	protected T initialize() {
		return supplier.get();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * get() can't throw because {@link #initialize()} doesn't throw.
	 */
	@Override
	@SneakyThrows(ConcurrentException.class)
	public T get() {
		return super.get();
	}

}
