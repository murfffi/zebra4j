package zebra4j;

import java.util.List;
import java.util.Random;

import zebra4j.Fact.BothTrue;
import zebra4j.Fact.Different;

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
	protected boolean rejectFact(Fact fact) {
		if (fact instanceof BothTrue && rejectBothTrue((BothTrue) fact)) {
			return true;
		}

		if (fact instanceof Different && rejectDifferent((Different) fact)) {
			return true;
		}

		return false;
	}

	private boolean rejectDifferent(Different fact) {
		if (fact.getRight().equals(Criminal.YES)) {
			return true;
		}

		return false;
	}

	private boolean rejectBothTrue(BothTrue fact) {
		if (fact.getLeft().equals(Criminal.YES) || fact.getRight().equals(Criminal.YES)) {
			return true;
		}

		return false;
	}

}
