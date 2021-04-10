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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;
import org.junit.Test;

import zebra4j.fact.BothTrue;
import zebra4j.fact.Different;

public class QuestionPuzzleSolverTest {

	@Test
	public void testSolve() {
		Puzzle basicPuzzle = new PuzzleGenerator(PuzzleGeneratorTest.simpleSolutionWithCriminal(),
				AbstractPuzzleGenerator.DEFAULT_FACT_TYPES).generate();
		QuestionPuzzle questionPuzzle = new QuestionPuzzle(Question.NAME_OF_CRIMINAL, basicPuzzle);
		QuestionPuzzleSolver solver = new QuestionPuzzleSolver(questionPuzzle);
		Collection<Attribute> solutionNames = solver.solve();
		assertEquals(1, solutionNames.size());
		assertEquals(PersonName.TYPE, solutionNames.iterator().next().type());
	}

	@Test
	public void testMinimal() {
		Question question = new Question(Clothes.GREEN, PersonName.TYPE);
		Map<AttributeType, Set<Attribute>> sets = new LinkedHashMap<>();
		sets.put(Clothes.TYPE, SetUtils.unmodifiableSet(Clothes.BLUE, Clothes.RED, Clothes.GREEN));
		sets.put(Criminal.TYPE, SetUtils.unmodifiableSet(Criminal.YES, Criminal.NO));
		sets.put(PersonName.TYPE, SetUtils.unmodifiableSet(PersonName.ELENA, PersonName.IVAN, PersonName.PETER));
		sets.put(AtHouse.TYPE, new HashSet<>(AtHouse.TYPE.getAttributes(3)));
		Puzzle puzzle = new Puzzle(sets, SetUtils.unmodifiableSet(new BothTrue(PersonName.PETER, Clothes.BLUE),
				new Different(PersonName.IVAN, Clothes.GREEN)));
		Collection<Attribute> solutions = new QuestionPuzzleSolver(new QuestionPuzzle(question, puzzle)).solve();
		assertEquals(Arrays.asList(PersonName.ELENA), new ArrayList<>(solutions));
	}

}
