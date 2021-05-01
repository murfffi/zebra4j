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
package zebra4j;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import lombok.SneakyThrows;
import zebra4j.fact.BothTrue;
import zebra4j.fact.CommutativeFact;
import zebra4j.fact.Different;

/**
 * Tests of the grammar of descriptions of various integrations of facts,
 * attributes and questions.
 * 
 * <p>
 * All grammar class are in the same test class (fixture) because the creation
 * or first usage of the language tool is slow: 2-3 secs on a fast laptop.
 */
public class GrammarIT {

	private static final Attribute EXAMPLE_PET = Attributes.PET.getAttributes(1).get(0);
	private final JLanguageTool englishTool = new JLanguageTool(new AmericanEnglish());

	private String describeSet(AttributeType type) {
		Collection<Attribute> exampleAttributes = type.getAttributes(3);
		return type.describeSet(exampleAttributes, Locale.ENGLISH);
	}

	private void testGrammarDescribeSet(AttributeType type) {
		String setDescriptions = describeSet(type);
		testEnglishGrammar(setDescriptions);
	}

	private void testGrammar(Function<Locale, String> describe) {
		String sentence = describe.apply(Locale.ENGLISH);
		testEnglishGrammar(sentence);
	}

	@SneakyThrows(IOException.class)
	private void testEnglishGrammar(String sentence) {
		List<RuleMatch> matches = englishTool.check(sentence);
		assertTrue(matches.toString(), matches.isEmpty());
	}

	private void testFactGrammar(CommutativeFact.Source factSource) {
		testGrammar(factSource.create(PersonName.ELENA, EXAMPLE_PET)::describe);
		testGrammar(factSource.create(Clothes.GREEN, new AtHouse(1))::describe);
	}

	@Test
	public void testPetDescribe_Grammar() {
		testGrammar(new Question(Clothes.GREEN, Attributes.PET)::describe);
		testGrammar(new Question(EXAMPLE_PET, PersonName.TYPE)::describe);
		testGrammarDescribeSet(Attributes.PET);
	}

	@Test
	public void testClothes_DescribeSet_Grammar() {
		testGrammarDescribeSet(Clothes.TYPE);
	}

	@Test
	public void testBothTrue_Describe_Grammar() {
		testFactGrammar(BothTrue::new);
	}

	@Test
	public void testDifferent_Describe_Grammar() {
		testFactGrammar(Different::new);
	}
}
