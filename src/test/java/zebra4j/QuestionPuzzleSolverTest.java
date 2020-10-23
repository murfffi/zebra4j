package zebra4j;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class QuestionPuzzleSolverTest {

	@Test
	public void testSolve() {
		Puzzle basicPuzzle = new PuzzleGenerator().generate(PuzzleGeneratorTest.simpleSolutionWithCriminal());
		QuestionPuzzle questionPuzzle = QuestionPuzzle.nameOfCriminal(basicPuzzle);
		QuestionPuzzleSolver solver = new QuestionPuzzleSolver(questionPuzzle);
		List<Attribute> solutionNames = solver.solve();
		assertEquals(1, solutionNames.size());
		assertEquals(PersonName.TYPE, solutionNames.get(0).type());
	}

}
