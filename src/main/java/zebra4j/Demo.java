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

import java.util.Locale;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;

import zebra4j.Cli.GeneratedBasicPuzzle;
import zebra4j.Cli.GeneratedQuestionPuzzle;
import zebra4j.fact.BothTrue;
import zebra4j.fact.NearbyHouse;
import zebra4j.util.JDKRandom;

/**
 * Sample app
 */
class Demo {

	private static final int NUM_PEOPLE = 4;

	public static void main(String[] args) {
		demo();
	}

	static void demo() {
		generateAndSolveQuestionPuzzle();
		System.out.println("----------");
		generateAndSolveBasicPuzzle();
		System.out.println("----------");
		customQuestionPuzzle();
		System.out.println("----------");
		solvePredefinedPuzzle();
		System.out.println("----------");
	}

	/**
	 * Generates and solves a basic {@link BasicPuzzle} with default configuration
	 */
	public static void generateAndSolveBasicPuzzle() {
		System.out.println("Basic puzzle:");
		BasicPuzzle puzzle = PuzzleGenerator.randomPuzzle(NUM_PEOPLE);
		PuzzleSolution solution = new PuzzleSolver(puzzle).solve().get(0);
		Cli.printGeneratedBasicPuzzle(new GeneratedBasicPuzzle(null, puzzle, solution), Locale.getDefault(),
				System.out);
	}

	/**
	 * Generates and solves a {@link QuestionPuzzle} with default configuration
	 */
	public static void generateAndSolveQuestionPuzzle() {
		System.out.println("Question puzzle:");
		QuestionPuzzle puzzle = QuestionPuzzleGenerator.randomPuzzle(NUM_PEOPLE);
		Attribute solution = new QuestionPuzzleSolver(puzzle).solveSingle();
		Cli.printGeneratedQuestionPuzzle(new GeneratedQuestionPuzzle(null, puzzle, solution), Locale.getDefault(),
				System.out);
	}

	/**
	 * Generates and solves a {@link QuestionPuzzle} with custom configuration
	 */
	public static void customQuestionPuzzle() {
		System.out.println("Custom question puzzle:");
		// Check out the definition of Attributes.PET to learn the easiest way to define
		// your own attributes.
		Set<AttributeType> attributeTypes = SetUtils.unmodifiableSet(Attributes.PET, Attributes.NAME,
				Attributes.AT_HOUSE);
		PuzzleSolution sampleSolution = new SolutionGenerator(attributeTypes, NUM_PEOPLE, new JDKRandom()).generate();
		QuestionPuzzleGenerator generator = new QuestionPuzzleGenerator(Question.generate(sampleSolution),
				sampleSolution, AbstractPuzzleGenerator.DEFAULT_FACT_TYPES);
		QuestionPuzzle puzzle = generator.generate();
		Attribute solution = new QuestionPuzzleSolver(puzzle).solveSingle();
		Cli.printGeneratedQuestionPuzzle(new GeneratedQuestionPuzzle(null, puzzle, solution), Locale.getDefault(),
				System.out);
	}

	/**
	 * Solves a custom puzzle defined using the Java API. The example uses the
	 * original Zebra puzzle
	 */
	public static void solvePredefinedPuzzle() {
		System.out.println("Solving original zebra puzzle (see code):");
		// Configures the original zebra puzzle from
		// https://en.wikipedia.org/wiki/Zebra_Puzzle
		PuzzleBuilder builder = new PuzzleBuilder();

		// Attribute sets
		// There are five houses.
		builder.addSet(AtHouse.TYPE.getAttributes(5).toArray(new AtHouse[] {}));
		// Each person is of one of five nationalities.
		BasicAttributeType nationality = builder.addSet("Nationality", "is a Spaniard", "is an Englishman",
				"is Ukranian", "is Norwegian", "is Japanese");
		// Each person drinks a different favorite beverage.
		BasicAttributeType beverage = builder.addSet("Beverage", "drinks coffee", "drinks tea", "drinks milk",
				"drinks water", "drinks orange juice");
		BasicAttributeType pet = builder.addSet("Pet", "owns a zebra", //
				"owns snails", //
				"owns a dog", //
				"owns a horse", //
				"owns a fox");
		BasicAttributeType houseColor = builder.addSet("House color", "lives in the green house",
				"lives in the red house", "lives in the yellow house", "lives in the blue house",
				"lives in the ivory house");
		BasicAttributeType brand = builder.addSet("Smokes brand", "smokes Kools", "smokes Old Gold",
				"smokes Lucky Strike", "smokes Parliaments", "smokes Chesterfields");

		// Facts

		// The Englishman lives in the red house.
		builder.addFact(new BothTrue(nationality.findByLabel("is an Englishman").get(),
				houseColor.findByLabel("lives in the red house").get()));

		// The Spaniard owns the dog.
		builder.addFact(
				new BothTrue(nationality.findByLabel("is a Spaniard").get(), pet.findByLabel("owns a dog").get()));

		// Coffee is drunk in the green house.
		builder.addFact(new BothTrue(beverage.findByLabel("drinks coffee").get(),
				houseColor.findByLabel("lives in the green house").get()));

		// The Ukrainian drinks tea.
		builder.addFact(
				new BothTrue(nationality.findByLabel("is Ukranian").get(), beverage.findByLabel("drinks tea").get()));

		// The green house is immediately to the right of the ivory house.
		// TODO "To the left/right of" facts are not supported by zebra4j.
		// Instead, we add: The green house is next to the ivory house.
		builder.addFact(new NearbyHouse(1, houseColor.findByLabel("lives in the green house").get(),
				houseColor.findByLabel("lives in the ivory house").get()));

		// The Old Gold smoker owns snails.
		builder.addFact(new BothTrue(brand.findByLabel("smokes Old Gold").get(), pet.findByLabel("owns snails").get()));

		// Kools are smoked in the yellow house.
		builder.addFact(new BothTrue(brand.findByLabel("smokes Kools").get(),
				houseColor.findByLabel("lives in the yellow house").get()));

		// Milk is drunk in the middle house.
		builder.addFact(new BothTrue(beverage.findByLabel("drinks milk").get(), new AtHouse(3)));

		// The Norwegian lives in the first house.
		builder.addFact(new BothTrue(nationality.findByLabel("is Norwegian").get(), new AtHouse(1)));

		// The man who smokes Chesterfields lives in the house next to the man with the
		// fox.
		builder.addFact(new NearbyHouse(1, brand.findByLabel("smokes Chesterfields").get(),
				pet.findByLabel("owns a fox").get()));

		// Kools are smoked in the house next to the house where the horse is kept.
		builder.addFact(
				new NearbyHouse(1, brand.findByLabel("smokes Kools").get(), pet.findByLabel("owns a horse").get()));

		// The Lucky Strike smoker drinks orange juice.
		builder.addFact(new BothTrue(brand.findByLabel("smokes Lucky Strike").get(),
				beverage.findByLabel("drinks orange juice").get()));

		// The Japanese smokes Parliaments.
		builder.addFact(new BothTrue(brand.findByLabel("smokes Parliaments").get(),
				nationality.findByLabel("is Japanese").get()));

		// The Norwegian lives next to the blue house.
		builder.addFact(new NearbyHouse(1, nationality.findByLabel("is Norwegian").get(),
				houseColor.findByLabel("lives in the blue house").get()));

		BasicPuzzle puzzle = builder.build();

		// Solve
		PuzzleSolution solution = new PuzzleSolver(puzzle).solveToStream().findAny().get();

		Question whoDrinksWater = new Question(beverage.findByLabel("drinks water").get(), nationality);
		System.out.format("Who drinks water? The person who who %s.\n", whoDrinksWater.answer(solution).get());

		Question whoOwnsZebra = new Question(pet.findByLabel("owns a zebra").get(), nationality);
		System.out.format("Who owns the zebra? The person who %s.\n", whoOwnsZebra.answer(solution).get());

		// Full solution:
		Cli.printGeneratedBasicPuzzle(new GeneratedBasicPuzzle(null, puzzle, solution), Locale.getDefault(),
				System.out);
	}

}
