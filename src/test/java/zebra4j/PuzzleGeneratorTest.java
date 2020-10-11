package zebra4j;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class PuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.sampleSolution();
		Puzzle puzzle = new PuzzleGenerator().generate(startSolution);
		Collection<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertTrue(result.contains(startSolution));
		Assert.assertEquals(1, result.size());
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
