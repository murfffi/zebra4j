package zebra4j;

import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PuzzleSolverTest {

	@Test
	public void testUnique() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.sampleSolution();
		Puzzle puzzle = new PuzzleGenerator().generate(startSolution);
		List<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertEquals(result.size(), new HashSet<>(result).size());
	}

	@Test
	public void testSolverInternal() {
		Puzzle puzzle = new PuzzleGenerator().generate(PuzzleGeneratorTest.sampleSolution());
		Assert.assertTrue(new PuzzleSolver(puzzle).solveChoco().size() > 0);
	}

}
