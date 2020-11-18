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

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import zebra4j.PuzzleGeneratorTest;
import zebra4j.PuzzleSolution;

public class BothTrueTest {

	@Test
	public void testGenerate() {
		testGenerate(BothTrue.TYPE);
	}

	public static void testGenerate(Fact.Type type) {
		PuzzleSolution solution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		List<Fact> facts = type.generate(solution);
		facts.stream().forEach(f -> assertTrue(f.appliesTo(solution)));
	}

}
