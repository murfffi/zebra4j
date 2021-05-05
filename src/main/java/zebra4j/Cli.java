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

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;
import zebra4j.Cli.DemoCli;
import zebra4j.Cli.GenerateCli;

/**
 * CLI for zebra4j
 */
@Command(name = "zebra4j", mixinStandardHelpOptions = true, description = "Generates logic grid (zebra) puzzles.", subcommands = {
		DemoCli.class, GenerateCli.class }, versionProvider = Cli.VersionProvider.class, showDefaultValues = true)
public class Cli {

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

	@Command(name = "generate", mixinStandardHelpOptions = true, showDefaultValues = true)
	static class GenerateCli implements Runnable {

		@Option(names = { "-t", "--type" }, defaultValue = "QUESTION")
		PuzzleType type = PuzzleType.QUESTION;

		@Option(names = { "-o", "--output" }, defaultValue = "TEXT")
		OutputFormat output = OutputFormat.TEXT;

		@Option(names = { "--seed" })
		Long seed;

		@Option(names = { "-p", "--people" }, defaultValue = "4")
		int people = 4;

		@Option(names = { "--locale" }, defaultValue = "en")
		Locale locale = Locale.ENGLISH;

		PrintStream out = System.out;

		@Override
		public void run() {
			if (seed == null) {
				seed = System.currentTimeMillis();
			}

			switch (type) {
			case QUESTION:
				printQuestionPuzzle();
				break;
			case BASIC:
				printBasicPuzzle();
				break;
			default:
				break;
			}
		}

		private void printBasicPuzzle() {
			GeneratedBasicPuzzle sample = Cli.sampleBasicPuzzle(seed, people);
			Cli.printGeneratedBasicPuzzle(sample, locale, out);
		}

		private void printQuestionPuzzle() {
			GeneratedQuestionPuzzle sample = Cli.sampleQuestionPuzzle(seed, people);
			Cli.printGeneratedQuestionPuzzle(sample, locale, out);
		}

	}

	@AllArgsConstructor
	static class GeneratedQuestionPuzzle {
		final Optional<Long> seed;
		final QuestionPuzzle puzzle;
		final Attribute answer;
	}

	@AllArgsConstructor
	static class GeneratedBasicPuzzle {
		final Optional<Long> seed;
		final Puzzle puzzle;
		final PuzzleSolution answer;
	}

	static GeneratedQuestionPuzzle sampleQuestionPuzzle(long seed, int people) {
		Random rnd = new Random(seed);
		PuzzleSolution sampleSolution = new SolutionGenerator(Attributes.DEFAULT_TYPES, people, rnd).generate();
		Question question = Question.generate(sampleSolution.getAttributeSets(), rnd);
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(question, sampleSolution, rnd,
				QuestionPuzzleGenerator.DEFAULT_FACT_TYPES);
		QuestionPuzzle puzzle = generator.generate();
		Attribute answer = puzzle.getQuestion().answer(sampleSolution).get();
		return new GeneratedQuestionPuzzle(Optional.of(seed), puzzle, answer);
	}

	static void printGeneratedQuestionPuzzle(GeneratedQuestionPuzzle sample, Locale locale, PrintStream out) {
		out.println("Facts:");
		sample.puzzle.describeConstraints(locale).stream().forEach(out::println);
		AttributeType about = sample.puzzle.getQuestion().getAbout();
		out.println();
		out.println("Question: " + sample.puzzle.getQuestion().describe(locale));
		out.println("Answer options: " + sample.puzzle.getPuzzle().getAttributeSets().get(about).stream()
				.map(a -> a.description(locale)).collect(Collectors.joining(", ")));
		out.println("Answer: " + sample.answer.description(locale));
		sample.seed.ifPresent(seed -> out.println("Seed: " + seed));
	}

	static GeneratedBasicPuzzle sampleBasicPuzzle(long seed, int people) {
		Random rnd = new Random(seed);
		PuzzleSolution solution = new SolutionGenerator(Attributes.DEFAULT_TYPES, people, rnd).generate();
		PuzzleGenerator generator = new PuzzleGenerator(rnd, solution, QuestionPuzzleGenerator.DEFAULT_FACT_TYPES);
		Puzzle puzzle = generator.generate();
		return new GeneratedBasicPuzzle(Optional.of(seed), puzzle, solution);
	}

	static void printGeneratedBasicPuzzle(GeneratedBasicPuzzle sample, Locale locale, PrintStream out) {
		out.println("Facts:");
		sample.puzzle.describeConstraints(locale).stream().forEach(out::println);
		out.println();
		out.println("Solution:");
		Stream.of(sample.answer.describe(locale))
				.forEach(solutionPerson -> out.println(Arrays.toString(solutionPerson)));
		sample.seed.ifPresent(seed -> out.println("Seed: " + seed));
	}

	static class VersionProvider implements IVersionProvider {

		@Override
		public String[] getVersion() throws Exception {
			Package pkg = this.getClass().getPackage();
			return new String[] { pkg.getImplementationVersion() };
		}

	}

	public static void main(String[] args) {
		CommandLine commandLine = new CommandLine(new Cli());
		commandLine.registerConverter(Locale.class, Locale::new);
		int exitCode = commandLine.execute(args);
		System.exit(exitCode);
	}
}
