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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.collections4.SetUtils;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.JLanguageTool;
import org.languagetool.JLanguageTool.Level;
import org.languagetool.JLanguageTool.Mode;
import org.languagetool.JLanguageTool.ParagraphHandling;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import zebra4j.fact.BothTrue;
import zebra4j.fact.CommutativeFact;
import zebra4j.fact.Different;
import zebra4j.util.LazyInstance;

/**
 * Tests of the grammar of descriptions of various integrations of facts,
 * attributes and questions.
 * 
 * <p>
 * All grammar class are in the same test class (fixture) because the creation
 * or first usage of the language tool is slow: 2-3 secs on a fast laptop.
 */
@Slf4j
public class GrammarIT {

	/**
	 * LanguageTool validation rule: all sentences must have at least one verb.
	 */
	private static class HasVerbRule extends Rule {

		/**
		 * Acceptable kinds of verbs, using standard POS (part-of-speech) tags.
		 * 
		 * <p>
		 * Only present tense is accepted. POS tags reference:
		 * https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
		 */
		private static final Set<String> ALLOWED = SetUtils.unmodifiableSet("VBZ", "VBP");

		@Override
		public String getId() {
			return this.getClass().getName();
		}

		@Override
		public String getDescription() {
			return getId();
		}

		@Override
		public RuleMatch[] match(AnalyzedSentence sentence) throws IOException {
			Optional<?> match = Stream.of(sentence.getTokensWithoutWhitespace())
					.flatMap(token -> token.getReadings().stream()).map(AnalyzedToken::getPOSTag)
					.filter(ALLOWED::contains).findAny();
			if (!match.isPresent()) {
				return new RuleMatch[] { new RuleMatch(this, sentence, 0, sentence.getCorrectedTextLength(),
						"No verb in " + sentence.getText()) };
			}
			return new RuleMatch[0];
		}

	}

	private static final Attribute EXAMPLE_PET = Attributes.PET.getAttributes(1).get(0);
	private static final LazyInstance<JLanguageTool> LAZY_TOOL = new LazyInstance<>(GrammarIT::makeTool);

	private final JLanguageTool englishTool = LAZY_TOOL.get();

	@SneakyThrows
	private static JLanguageTool makeTool() {
		JLanguageTool tool = new JLanguageTool(new AmericanEnglish());
		String ngramsPathEnv = System.getenv("ZEBRA_NGRAMS_PATH");
		log.info("Looking for ngrams given env var: {}", ngramsPathEnv);
		if (ngramsPathEnv != null) {
			Path ngramsPath = Paths.get(ngramsPathEnv);
			if (Files.exists(ngramsPath)) {
				log.info("Loading ngrams from {}", ngramsPath);
				tool.activateLanguageModelRules(ngramsPath.toFile());
			}
		}
		tool.addRule(new HasVerbRule());
		return tool;
	}

	private void testGrammarDescribeSet(AttributeType type) {
		Collection<Attribute> exampleAttributes = type.getAttributes(3);
		testGrammar(locale -> type.describeSet(exampleAttributes, locale));
	}

	private void testGrammar(Function<Locale, String> describe) {
		// This will be extended when the languages supported both by JLanguageTool and
		// zebra4j are not just English.
		String sentence = describe.apply(Locale.ENGLISH);
		testEnglishGrammar(sentence);
	}

	@SneakyThrows(IOException.class)
	private void testEnglishGrammar(String sentence) {
		AnnotatedText text = new AnnotatedTextBuilder().addText(sentence).build();
		List<RuleMatch> matches = englishTool.check(text, true, ParagraphHandling.NORMAL, null, Mode.ALL, Level.PICKY);
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
	public void testPersonName_DescribeSet_Grammar() {
		testGrammarDescribeSet(Attributes.NAME);
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
