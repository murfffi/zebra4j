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
package zebra4j.cli;

import java.util.Locale;

import org.apache.commons.lang3.RandomUtils;

import zebra4j.cli.Cli.GeneratedQuestionPuzzle;

/**
 * Generates the content of SAMPLES.md in the repo root
 * 
 * <p>
 * The generation is currently fast with 4-person puzzles, but if it bumped to
 * more complex puzzles, it needs to switch to a parallel stream implementation:
 * 
 * <pre>
 * Stream.generate.parallel.filter.limit.forEach
 * </pre>
 */
public class GenerateSamples {

	public static void main(String[] args) {
		int samples = 0;
		Locale locale = Locale.ENGLISH;
		while (samples < 10) {
			GeneratedQuestionPuzzle sample = Cli.sampleQuestionPuzzle(RandomUtils.nextLong(0, Long.MAX_VALUE), 4);
			if (sample.puzzle.getBasicPuzzle().getFacts().size() > 6) {
				Cli.printGeneratedQuestionPuzzle(sample, locale, System.out);
				System.out.println("----------------------------");
				++samples;
			}
		}

	}

}
