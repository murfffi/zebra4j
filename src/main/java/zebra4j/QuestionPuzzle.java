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

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

import lombok.Value;
import zebra4j.fact.Fact;

/**
 * A puzzle that looks for answer to the given question given rules defined by
 * the attribute sets used in the puzzle (e.g. all people have different name
 * and the name may be Liza, John or Mary) and a set of {@link Fact}s known
 * about the people in the puzzle.
 */
@Value
public class QuestionPuzzle {

	private final Question question;
	private final Puzzle puzzle;

	public QuestionPuzzle(Question question, Puzzle puzzle) {
		Validate.isTrue(question.appliesTo(puzzle), "Question %s does not apply to puzzle %s", question, puzzle);
		this.question = question;
		this.puzzle = puzzle;
	}

	/**
	 * Same as {@link Puzzle#describeConstraints(Locale)}
	 * 
	 * @param locale required
	 * @return a list of sentences in the given locale, not null.
	 */
	public List<String> describeConstraints(Locale locale) {
		Set<AttributeType> referencedTypes = puzzle.getFacts().stream().flatMap(f -> f.attributeTypes().stream())
				.collect(Collectors.toSet());
		return puzzle.describeConstraintsForTypes(locale, referencedTypes);
	}

}
