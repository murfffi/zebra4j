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
package zebra4j;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.Validate;

import zebra4j.Fact.BothTrue;
import zebra4j.Fact.Different;

public class QuestionPuzzleGenerator extends AbstractPuzzleGenerator<QuestionPuzzle> {

	private final Question question;

	public static QuestionPuzzleGenerator nameOfCriminal() {
		return new QuestionPuzzleGenerator(Question.NAME_OF_CRIMINAL);
	}

	public QuestionPuzzleGenerator(Question question) {
		super(new Random());
		this.question = question;
	}

	@Override
	protected QuestionPuzzle toPuzzle(PuzzleSolution solution, List<Fact> facts) {
		Validate.isTrue(question.appliesTo(solution), "Question %s does not apply to solution %s", question, solution);
		return new QuestionPuzzle(question, PuzzleGenerator.toBasicPuzzle(solution, facts));
	}

	@Override
	protected CountingSolver createSolver(QuestionPuzzle puzzle) {
		return new QuestionPuzzleSolver(puzzle);
	}

	@Override
	protected boolean rejectFact(Fact fact) {
		if (fact instanceof BothTrue && rejectBothTrue((BothTrue) fact)) {
			return true;
		}

		if (fact instanceof Different && rejectDifferent((Different) fact)) {
			return true;
		}

		return false;
	}

	private boolean rejectDifferent(Different fact) {
		if (fact.getRight().equals(Criminal.YES)) {
			return true;
		}

		return false;
	}

	private boolean rejectBothTrue(BothTrue fact) {
		if (fact.getLeft().equals(Criminal.YES) || fact.getRight().equals(Criminal.YES)) {
			return true;
		}

		return false;
	}

}
