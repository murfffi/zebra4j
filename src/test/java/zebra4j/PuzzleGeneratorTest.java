package zebra4j;

import org.junit.Test;

public class PuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolution solution = sampleSolution();
		Puzzle generated = PuzzleGenerator.generate(solution);
	}

	public static PuzzleSolution sampleSolution() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.ПЕТЪР, Clothes.СИНИ);
		builder.addWithHouse(PersonName.ГЕОРГИ, Clothes.ЖЪЛТИ);
		builder.addWithHouse(PersonName.ИВАН, Clothes.ЗЕЛЕНИ);
		PuzzleSolution solution = builder.build();
		return solution;
	}

}
