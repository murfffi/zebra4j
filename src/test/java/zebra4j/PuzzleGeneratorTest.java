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
package zebra4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static zebra4j.Attributes.AT_HOUSE;
import static zebra4j.Attributes.CLOTHES;
import static zebra4j.Attributes.CRIMINAL;
import static zebra4j.Attributes.NAME;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;
import org.junit.Ignore;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import zebra4j.fact.BothTrue;
import zebra4j.fact.Different;
import zebra4j.fact.Fact;
import zebra4j.fact.NearbyHouse;

@Slf4j
public class PuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.sampleSolution();
		Puzzle puzzle = new PuzzleGenerator(startSolution, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		Collection<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		assertTrue(result.contains(startSolution));
		assertEquals(1, result.size());
	}

	@Test
	public void testGenerate_Criminal() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Puzzle puzzle = new PuzzleGenerator(startSolution, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		log.trace("Puzzle is {}", puzzle);
		Collection<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		assertEquals(1, result.size());
		PuzzleSolution solution = result.iterator().next();
		assertEquals(solution.toString(), 2, solution.getPeople().size());
		SolutionPerson criminal = startSolution.findPerson(Criminal.YES).get();
		assertTrue(solution.getPeople().contains(criminal));
	}

	@Test
	@Ignore
	public void testGenerate_StableBySeed() throws Exception {
		Random rnd = new Random(1614459213067L);
		Set<AttributeType> types = SetUtils.unmodifiableSet(NAME, AT_HOUSE, CLOTHES, CRIMINAL);
		SolutionGenerator solg = new SolutionGenerator(types, 3, rnd);
		PuzzleSolution sol = solg.generate();

		PuzzleGenerator gen = new PuzzleGenerator(rnd, sol, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES);
		Puzzle puzzle = gen.generate();

		Set<Fact> expectedFacts = new LinkedHashSet<>(Arrays.asList( //
				new BothTrue(PersonName.PETER, Clothes.BLUE), //
				new Different(PersonName.ELENA, Criminal.YES), //
				new Different(PersonName.IVAN, new AtHouse(3)), //
				new Different(Clothes.BLUE, Criminal.YES), //
				new Different(PersonName.ELENA, Clothes.GREEN), //
				new NearbyHouse(2, PersonName.IVAN, PersonName.PETER)));
		assertEquals(expectedFacts, puzzle.getFacts());
	}

	public static PuzzleSolution sampleSolution() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.PETER, Clothes.BLUE);
		builder.addWithHouse(PersonName.GEORGE, Clothes.YELLOW);
		builder.addWithHouse(PersonName.IVAN, Clothes.GREEN);
		PuzzleSolution solution = builder.build();
		return solution;
	}

	public static PuzzleSolution simpleSolutionWithCriminal() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.PETER, Criminal.NO);
		builder.addWithHouse(PersonName.GEORGE, Criminal.YES);
		PuzzleSolution startSolution = builder.build();
		return startSolution;
	}

}
