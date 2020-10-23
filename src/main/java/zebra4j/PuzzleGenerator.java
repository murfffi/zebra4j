package zebra4j;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator extends AbstractPuzzleGenerator<Puzzle> {

	public PuzzleGenerator() {
		super(new Random());
	}

	@Override
	protected Puzzle toPuzzle(PuzzleSolution solution, List<Fact> facts) {
		return toBasicPuzzle(solution, facts);
	}

	public static Puzzle toBasicPuzzle(PuzzleSolution solution, List<Fact> facts) {
		return new Puzzle(solution.getAttributeSets(), new LinkedHashSet<>(facts));
	}

	@Override
	protected CountingSolver createSolver(Puzzle puzzle) {
		return new PuzzleSolver(puzzle);
	}

}
