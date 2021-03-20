/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 - 2021 Marin Nozhchev
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

import java.io.PrintStream;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.Value;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;
import zebra4j.Cli.DemoCli;
import zebra4j.Cli.GenerateCli;

@Command(name = "zebra4j", mixinStandardHelpOptions = true, description = "Generates logic grid (zebra) puzzles.", subcommands = {
		DemoCli.class, GenerateCli.class }, versionProvider = Cli.VersionProvider.class, showDefaultValues = true)
public class Cli {

	enum PuzzleType {
		QUESTION, BASIC;
	}

	enum OutputFormat {
		TEXT, JSON;
	}

	@Command(name = "demo")
	static class DemoCli implements Runnable {

		@Override
		public void run() {
			Demo.demo();
		}
	}

	@Command(name = "generate")
	static class GenerateCli implements Runnable {

		@Option(names = { "-t", "--type" }, defaultValue = "QUESTION")
		PuzzleType type;

		@Option(names = { "-o", "--output" }, defaultValue = "TEXT")
		OutputFormat output;

		@Option(names = { "--seed" })
		Long seed;

		@Option(names = { "-p", "--people" }, defaultValue = "4")
		int people;

		@Option(names = { "--locale" }, defaultValue = "en")
		Locale locale;

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
			default:
				break;
			}
		}

		private void printBasicPuzzle() {
			// TODO Auto-generated method stub

		}

		private void printQuestionPuzzle() {
			QSample sample = sampleQuestionPuzzle(seed, people);
			Cli.printQuestionPuzzle(sample, locale, out);
		}

	}

	@Value
	static class QSample {
		long seed;
		QuestionPuzzle puzzle;
		Attribute answer;
	}

	static QSample sampleQuestionPuzzle(long seed, int people) {
		Random rnd = new Random(seed);
		PuzzleSolution sampleSolution = new SolutionGenerator(Attributes.DEFAULT_TYPES, people, rnd).generate();
		Question question = Question.generate(sampleSolution.getAttributeSets(), rnd);
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(question, sampleSolution, rnd,
				QuestionPuzzleGenerator.DEFAULT_FACT_TYPES);
		QuestionPuzzle puzzle = generator.generate();
		Attribute answer = puzzle.getQuestion().answer(sampleSolution).get();
		return new QSample(seed, puzzle, answer);
	}

	static void printQuestionPuzzle(QSample sample, Locale locale, PrintStream out) {
		out.println("Facts:");
		sample.puzzle.describeConstraints(locale).stream().forEach(out::println);
		AttributeType about = sample.puzzle.getQuestion().getAbout();
		out.println();
		out.println("Question: " + sample.puzzle.getQuestion().describe(locale));
		out.println("Answer options: " + sample.puzzle.getPuzzle().getAttributeSets().get(about).stream()
				.map(a -> a.description(locale)).collect(Collectors.joining(", ")));
		out.println("Answer: " + sample.answer.description(locale));
		out.println("Seed: " + sample.seed);
	}

	static class VersionProvider implements IVersionProvider {

		@Override
		public String[] getVersion() throws Exception {
			return new String[] { this.getClass().getPackage().getImplementationVersion() };
		}

	}

	public static void main(String[] args) {
		CommandLine commandLine = new CommandLine(new Cli());
		commandLine.registerConverter(Locale.class, Locale::new);
		int exitCode = commandLine.execute(args);
		System.exit(exitCode);
	}
}
