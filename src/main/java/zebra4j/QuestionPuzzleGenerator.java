package zebra4j;

import java.util.List;
import java.util.Random;

import zebra4j.Fact.BothTrue;

public class QuestionPuzzleGenerator extends AbstractPuzzleGenerator<QuestionPuzzle> {

	private final Attribute question;
	private final AttributeType id;

	public static QuestionPuzzleGenerator nameOfCriminal() {
		return new QuestionPuzzleGenerator(Criminal.YES, PersonName.TYPE);
	}

	public QuestionPuzzleGenerator(Attribute question, AttributeType id) {
		super(new Random());
		this.question = question;
		this.id = id;
	}

	@Override
	protected QuestionPuzzle toPuzzle(PuzzleSolution solution, List<Fact> facts) {
		return new QuestionPuzzle(question, id, PuzzleGenerator.toBasicPuzzle(solution, facts));
	}

	@Override
	protected CountingSolver createSolver(QuestionPuzzle puzzle) {
		return new QuestionPuzzleSolver(puzzle);
	}

	@Override
	protected boolean acceptFact(BothTrue fact) {
		return !fact.getLeft().equals(Criminal.YES) && !fact.getRight().equals(Criminal.YES);
	}

}
