package zebra4j;

import org.junit.Test;

public class PuzzleSolverTest {

	@Test
	public void testSolver() {
		Puzzle puzzle = PuzzleGenerator.generate(PuzzleGeneratorTest.sampleSolution());
		Object result = new PuzzleSolver(puzzle).solve();
		System.out.println(result);
	}

	@Test
	public void testSolverInternal() {
		Puzzle puzzle = PuzzleGenerator.generate(PuzzleGeneratorTest.sampleSolution());
		Object result = new PuzzleSolver(puzzle).solveInternal();
		System.out.println(result);
	}

}
