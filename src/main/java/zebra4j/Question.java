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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import lombok.Value;

/**
 * A question about an attribute of a person
 * 
 * <p>
 * The question is towards the person, identified by the attribute in
 * {@link #getTowards()} and is about the attribute of type {@link #getAbout()}.
 */
@Value
public class Question {
	public static Question NAME_OF_CRIMINAL = new Question(Criminal.YES, PersonName.TYPE);

	/**
	 * Generates a random question about the people in the given solution
	 * 
	 * @param solution
	 * @return a question that "appliesTo" the given solution
	 */
	public static Question generate(PuzzleSolution solution) {
		return generate(solution.getAttributeSets(), new Random());
	}

	/**
	 * Generates a random question using the given sets of attributes
	 * 
	 * @param attributeSets
	 * @param rnd
	 * @return a question, not null
	 */
	public static Question generate(Map<AttributeType, Set<Attribute>> attributeSets, Random rnd) {
		List<AttributeType> types = new ArrayList<>(attributeSets.keySet());
		int aboutCount = (int) aboutTypeStream(types).count();
		Optional<AttributeType> about = aboutTypeStream(types).skip(rnd.nextInt(aboutCount)).findAny();
		validate(about);
		Optional<AttributeType> towardsType = types.stream().filter(t -> t != about.get())
				.skip(rnd.nextInt(types.size() - 1)).findAny();
		validate(towardsType);
		Attribute towards;
		if (towardsType.get() == Criminal.TYPE) {
			towards = Criminal.YES;
		} else {
			List<Attribute> attributes = new ArrayList<>(attributeSets.get(towardsType.get()));
			towards = attributes.get(rnd.nextInt(attributes.size()));
		}
		return new Question(towards, about.get());
	}

	private static void validate(Optional<AttributeType> type) {
		if (!type.isPresent()) {
			throw new IllegalArgumentException("Not enough suitable attributes.");
		}
	}

	private static Stream<AttributeType> aboutTypeStream(List<AttributeType> types) {
		return types.stream().filter(t -> t instanceof AllDifferentType);
	}

	private final Attribute towards;
	private final AttributeType about;

	public String toSentence() {
		return about.questionSentencePart() + " " + towards.description() + "?";
	}

	public Optional<Attribute> answer(PuzzleSolution solution) {
		return solution.findPerson(towards).flatMap(person -> Optional.ofNullable(person.findAttribute(about)));
	}

	public boolean appliesTo(Puzzle puzzle) {
		return appliesTo(puzzle.getAttributeSets());
	}

	public boolean appliesTo(PuzzleSolution solution) {
		return appliesTo(solution.getAttributeSets());
	}

	private boolean appliesTo(Map<AttributeType, Set<Attribute>> attributeSets) {
		boolean towardsApplies = attributeSets.getOrDefault(towards.type(), Collections.emptySet()).contains(towards);
		return towardsApplies && attributeSets.containsKey(about);
	}

}
