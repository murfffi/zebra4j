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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Value;

@Value
public class QuestionPuzzle {

	private final Question question;
	private final Puzzle puzzle;

	public QuestionPuzzle(Question question, Puzzle puzzle) {
		Validate.isTrue(question.appliesTo(puzzle), "Question %s does not apply to puzzle %s", question, puzzle);
		this.question = question;
		this.puzzle = puzzle;
	}

	public static QuestionPuzzle nameOfCriminal(Puzzle puzzle) {
		return new QuestionPuzzle(Question.NAME_OF_CRIMINAL, puzzle);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(puzzle).append(question.toSentence())
				.build();
	}

}
