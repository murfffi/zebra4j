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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AllArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;
import zebra4j.Cli.DemoCli;
import zebra4j.Cli.GenerateCli;
import zebra4j.util.JDKRandom;
import zebra4j.util.Randomness;

/**
 * CLI for zebra4j
 */
@Command(name = "zebra4j", mixinStandardHelpOptions = true, description = "Generates logic grid (zebra) puzzles.", subcommands = {
		DemoCli.class, GenerateCli.class }, versionProvider = Cli.VersionProvider.class, showDefaultValues = true)
public class Cli {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	enum PuzzleType {
		QUESTION, BASIC;
	}

	enum OutputFormat {
		TEXT, JSON;
	}

	@Command(name = "demo", description = "Generates a few demo puzzles.")
	static class DemoCli implements Runnable {

		@Override
		public void run() {
			Demo.demo();
		}
	}

	@Command(name = "generate", mixinStandardHelpOptions = true, showDefaultValues = true, versionProvider = VersionProvider.class, description = "Generates a puzzle")
	static class GenerateCli implements Runnable {

		@Option(names = { "-t", "--type" }, defaultValue = "QUESTION", description = "Type of puzzle to generate")
		PuzzleType type = PuzzleType.QUESTION;

		@Option(names = { "-o", "--output" }, defaultValue = "TEXT", description = "Format of output")
		OutputFormat output = OutputFormat.TEXT;

		@Option(names = { "--seed" }, description = "Seed for the random generator")
		Long seed;

		@Option(names = { "-p", "--people" }, defaultValue = "4")
		int people = 4;

		@Option(names = { "--locale" }, defaultValue = "en")
		Locale locale = Locale.ENGLISH;

		/**
		 * out stream, modified in unit tests
		 */
		PrintStream out = System.out;

		@Override
		public void run() {
			if (seed == null) {
				seed = System.currentTimeMillis();
			}

			Object puzzle = null;
			switch (type) {
			case QUESTION:
				puzzle = Cli.sampleQuestionPuzzle(seed, people);
				break;
			case BASIC:
				Randomness rnd = new JDKRandom(seed);
				PuzzleSolution solution = new SolutionGenerator(Attributes.DEFAULT_TYPES, people, rnd).generate();
				PuzzleGenerator generator = new PuzzleGenerator(rnd, solution,
						QuestionPuzzleGenerator.DEFAULT_FACT_TYPES);
				puzzle = new GeneratedBasicPuzzle(seed, generator.generate(), solution);
				break;
			}

			if (output == OutputFormat.JSON) {
				try (Writer wout = new OutputStreamWriter(out)) {
					GSON.toJson(puzzle, puzzle.getClass(), wout);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return;
			}

			// OutputFormat.TEXT
			switch (type) {
			case QUESTION:
				Cli.printGeneratedQuestionPuzzle((GeneratedQuestionPuzzle) puzzle, locale, out);
				break;
			case BASIC:
				Cli.printGeneratedBasicPuzzle((GeneratedBasicPuzzle) puzzle, locale, out);
				break;
			}
		}

	}

	@AllArgsConstructor
	static class GeneratedQuestionPuzzle {
		// seed might be null; we don't use Optional because it might confuse JSON
		// serde
		final Long seed;
		final QuestionPuzzle puzzle;
		final Attribute answer;
	}

	@AllArgsConstructor
	static class GeneratedBasicPuzzle {
		final Long seed;
		final BasicPuzzle puzzle;
		final PuzzleSolution answer;
	}

	static GeneratedQuestionPuzzle sampleQuestionPuzzle(long seed, int people) {
		Randomness rnd = new JDKRandom(seed);
		PuzzleSolution sampleSolution = new SolutionGenerator(Attributes.DEFAULT_TYPES, people, rnd).generate();
		Question question = Question.generate(sampleSolution.getAttributeSets(), rnd);
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(question, sampleSolution, rnd,
				QuestionPuzzleGenerator.DEFAULT_FACT_TYPES);
		QuestionPuzzle puzzle = generator.generate();
		Attribute answer = puzzle.getQuestion().answer(sampleSolution).get();
		return new GeneratedQuestionPuzzle(seed, puzzle, answer);
	}

	static void printGeneratedQuestionPuzzle(GeneratedQuestionPuzzle sample, Locale locale, PrintStream out) {
		out.println("Facts:");
		sample.puzzle.describeConstraints(locale).stream().forEach(out::println);
		AttributeType about = sample.puzzle.getQuestion().getAbout();
		out.println();
		out.println("Question: " + sample.puzzle.getQuestion().describe(locale));
		out.println("Answer options: " + sample.puzzle.getBasicPuzzle().getAttributeSets().get(about).stream()
				.map(a -> a.description(locale)).collect(Collectors.joining(", ")));
		out.println("Answer: " + sample.answer.description(locale));
		if (sample.seed != null) {
			out.println("Seed: " + sample.seed);
		}
	}

	static void printGeneratedBasicPuzzle(GeneratedBasicPuzzle sample, Locale locale, PrintStream out) {
		out.println("Facts:");
		sample.puzzle.describeConstraints(locale).stream().forEach(out::println);
		out.println();
		out.println("Solution:");
		Stream.of(sample.answer.describe(locale))
				.forEach(solutionPerson -> out.println(Arrays.toString(solutionPerson)));
		if (sample.seed != null) {
			out.println("Seed: " + sample.seed);
		}
	}

	static class VersionProvider implements IVersionProvider {

		@Override
		public String[] getVersion() {
			Package pkg = this.getClass().getPackage();
			// That's Implementation-Version from MANIFEST.MF which is configured in pom.xml
			// (maven-jar-plugin). MANIFEST.MF is included by default in native image.
			return new String[] { pkg.getImplementationVersion() };
		}

	}

	public static void main(String[] args) {
		CommandLine commandLine = new CommandLine(new Cli());
		commandLine.registerConverter(Locale.class, Locale::new);
		commandLine.setCaseInsensitiveEnumValuesAllowed(true);
		int exitCode = commandLine.execute(args);
		System.exit(exitCode);
	}
}
