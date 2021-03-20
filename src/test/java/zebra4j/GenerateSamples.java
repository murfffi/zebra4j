/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 - 2021 Marin Nozhchev
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
package zebra4j;

import java.util.Locale;

import org.apache.commons.lang3.RandomUtils;

import zebra4j.Cli.QSample;

public class GenerateSamples {

	public static void main(String[] args) {
		int samples = 0;
		Locale locale = Locale.ENGLISH;
		while (samples < 10) {
			QSample sample = Cli.sampleQuestionPuzzle(RandomUtils.nextLong(0, Long.MAX_VALUE), 4);
			if (sample.getPuzzle().getPuzzle().getFacts().size() > 6) {
				Cli.printQuestionPuzzle(sample, locale, System.out);
				System.out.println("----------------------------");
				++samples;
			}
		}

	}

}
