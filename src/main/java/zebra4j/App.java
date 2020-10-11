package zebra4j;

/**
 * Sample app
 */
public class App {
	public static void main(String[] args) {
		PuzzleGenerator generator = new PuzzleGenerator();
		Puzzle puzzle = generator.generate(App.sampleSolution());
		System.out.println(puzzle);
		System.out.println(new PuzzleSolver(puzzle).solve());

	}

	public static PuzzleSolution sampleSolution() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.ПЕТЪР, Clothes.СИНИ);
		builder.addWithHouse(PersonName.ГЕОРГИ, Clothes.ЖЪЛТИ);
		builder.addWithHouse(PersonName.ИВАН, Clothes.ЗЕЛЕНИ);
		return builder.build();
	}
}
