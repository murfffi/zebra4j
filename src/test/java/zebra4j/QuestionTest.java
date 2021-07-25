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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import zebra4j.util.JDKRandom;

public class QuestionTest {

	@Test
	public void testGenerate() {
		PuzzleSolution solution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Question question = Question.generate(solution.getAttributeSets(), TestUtils.NOOP_RANDOM);
		assertTrue(String.format("Solution: %s, Question: %s", solution, question), question.appliesTo(solution));
	}

	@Test
	public void testGenerate_Stable() {
		PuzzleSolution solution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Question question = Question.generate(solution.getAttributeSets(), new JDKRandom(1));
		assertEquals(question, Question.generate(solution.getAttributeSets(), new JDKRandom(1)));
	}

	@Test
	public void testAnswer() {
		PuzzleSolution solution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Question question = Question.NAME_OF_CRIMINAL;
		assertEquals(PersonName.GEORGE, question.answer(solution).get());
	}

	@Test
	public void testAnswer_DoesNotApply() {
		PuzzleSolution solution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Question question = new Question(Criminal.YES,
				new BasicAttributeType(Collections.singleton("test"), "questionSentencePart %s", ""));
		assertFalse(question.answer(solution).isPresent());
	}

}
