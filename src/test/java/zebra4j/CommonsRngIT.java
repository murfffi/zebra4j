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
package zebra4j;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.apache.commons.rng.simple.JDKRandomBridge;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.Test;

public class CommonsRngIT {

	/**
	 * When compiling with TeaVM, users may want to replace the regular
	 * {@link Random} with an implementation from commons-rng, because the TeaVM
	 * Random doesn't support seed.
	 */
	@Test
	public void testCommonsRng() throws Exception {
		long seed = 1;
		Random rnd = new JDKRandomBridge(RandomSource.SPLIT_MIX_64, seed);
		PuzzleSolution sampleSolution = new SolutionGenerator(Attributes.DEFAULT_TYPES, 4, rnd).generate();
		Question question = Question.generate(sampleSolution.getAttributeSets(), rnd);
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(question, sampleSolution, rnd,
				QuestionPuzzleGenerator.DEFAULT_FACT_TYPES);
		QuestionPuzzle puzzle = generator.generate();
		assertEquals(4, puzzle.getPuzzle().getFacts().size());
	}

}
