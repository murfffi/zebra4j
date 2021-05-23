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

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class PerfIT {

	@Test
	public void testQuestion() throws Exception {
		PuzzleSolution sampleSolution = new SolutionGenerator(5).generate();
		Question question = Question.generate(sampleSolution);
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(question, sampleSolution,
				QuestionPuzzleGenerator.DEFAULT_FACT_TYPES);
		int numFacts = Stream.generate(generator).parallel().limit(5)
				.collect(Collectors.summingInt(p -> p.getPuzzle().getFacts().size()));
		assertTrue(numFacts > 5);
	}

}
