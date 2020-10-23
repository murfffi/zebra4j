package zebra4j;

import lombok.extern.slf4j.Slf4j;

/**
 * Sample app
 */
@Slf4j
public class App {
	public static void main(String[] args) {
		basicPuzzle();
		whoIsCriminal();
	}

	public static void basicPuzzle() {
		System.out.println("Basic puzzle:");
		PuzzleGenerator generator = new PuzzleGenerator();
		Puzzle puzzle = generator.generate(App.sampleSolution());
		System.out.println(puzzle);
		System.out.println(new PuzzleSolver(puzzle).solve());
	}

	public static void whoIsCriminal() {
		System.out.println("Who is criminal puzzle:");
		QuestionPuzzleGenerator generator = QuestionPuzzleGenerator.nameOfCriminal();
		QuestionPuzzle puzzle = generator.generate(App.sampleSolution());
		System.out.println(puzzle);
		System.out.println("Кой е престъпкник?");
		System.out.println(new QuestionPuzzleSolver(puzzle).solve());
	}

	public static PuzzleSolution sampleSolution() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.ПЕТЪР, Clothes.СИНИ, Criminal.YES);
		builder.addWithHouse(PersonName.ГЕОРГИ, Clothes.ЖЪЛТИ, Criminal.NO);
		builder.addWithHouse(PersonName.ИВАН, Clothes.ЗЕЛЕНИ, Criminal.NO);
		return builder.build();
	}
}
