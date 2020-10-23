package zebra4j;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class QuestionPuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		QuestionPuzzle puzzle = QuestionPuzzleGenerator.nameOfCriminal().generate(startSolution);
		List<Attribute> result = new QuestionPuzzleSolver(puzzle).solve();
		Assert.assertEquals(1, result.size());
	}

}
