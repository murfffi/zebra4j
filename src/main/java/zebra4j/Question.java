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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import lombok.Value;
import zebra4j.util.JDKRandom;
import zebra4j.util.Randomness;

/**
 * A question about an attribute of a person
 * 
 * <p>
 * The question is towards the person, identified by the attribute in
 * {@link #getTowards()} and is about the attribute of type {@link #getAbout()}.
 */
@Value
public class Question {

	/**
	 * Sample question: Who is the criminal?
	 */
	public static Question NAME_OF_CRIMINAL = new Question(Criminal.YES, PersonName.TYPE);

	/**
	 * Generates a random question about the people in the given solution
	 * 
	 * @param solution
	 * @return a question that "appliesTo" the given solution
	 */
	public static Question generate(PuzzleSolution solution) {
		return generate(solution.getAttributeSets(), new JDKRandom());
	}

	/**
	 * Generates a random question using the given sets of attributes
	 * 
	 * @param attributeSets
	 * @param rnd
	 * @return a question, not null
	 */
	public static Question generate(Map<AttributeType, Set<Attribute>> attributeSets, Randomness rnd) {
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

	/**
	 * Converts the question to a natural language sentence
	 * 
	 * @param locale
	 * @return
	 */
	public String describe(Locale locale) {
		String description = towards.description(locale);
		if (!PersonName.TYPE.equals(towards.type()) && !PersonName.TYPE.equals(about)
				&& Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			description = "the person who " + description;
		}
		return String.format(about.questionSentencePart(locale), description);
	}

	/**
	 * Answers this question about the given solution, if applicable
	 * 
	 * @param solution required
	 * @return the answer, present if this {@link #appliesTo} solution
	 */
	public Optional<Attribute> answer(PuzzleSolution solution) {
		return solution.findPerson(towards).flatMap(person -> Optional.ofNullable(person.findAttribute(about)));
	}

	/**
	 * Answers this question about the given raw solution, if applicable
	 * 
	 * @param rawSolution solution, as in {@link PuzzleSolver#solveChoco()}
	 * @return a stream with one element - the answer - if applicable, empty
	 *         otherwise
	 */
	Stream<Attribute> answer(Map<Attribute, Integer> rawSolution) {
		int personId = rawSolution.get(towards);
		Set<Attribute> attributes = rawSolution.keySet();
		return attributes.stream().filter(attr -> about.equals(attr.type()) && rawSolution.get(attr) == personId);
	}

	/**
	 * @param puzzle a basic (full assignment) puzzle
	 * @return if the question is relevant to the puzzle i.e. the puzzle includes
	 *         the {@link AttributeType} in "about" and the {@link Attribute} in
	 *         "towards"
	 */
	public boolean appliesTo(BasicPuzzle puzzle) {
		return appliesTo(puzzle.getAttributeSets());
	}

	/**
	 * @param solution required
	 * @return same as {@link #appliesTo(BasicPuzzle)} but for the given solution
	 */
	public boolean appliesTo(PuzzleSolution solution) {
		return appliesTo(solution.getAttributeSets());
	}

	private boolean appliesTo(Map<AttributeType, Set<Attribute>> attributeSets) {
		boolean towardsApplies = attributeSets.getOrDefault(towards.type(), Collections.emptySet()).contains(towards);
		return towardsApplies && attributeSets.containsKey(about);
	}

}
