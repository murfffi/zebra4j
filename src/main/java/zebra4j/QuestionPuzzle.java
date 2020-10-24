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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Value;

@Value
public class QuestionPuzzle {

	private final Attribute question;
	private final AttributeType id;
	private final Puzzle puzzle;

	public String describeQuestion() {
		return id.questionSentencePart() + " " + question.description() + "?";
	}

	public static QuestionPuzzle nameOfCriminal(Puzzle puzzle) {
		return new QuestionPuzzle(Criminal.YES, PersonName.TYPE, puzzle);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(puzzle).append(describeQuestion())
				.build();
	}
}
