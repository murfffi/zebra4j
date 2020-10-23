package zebra4j;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionPuzzleSolver {

	private final QuestionPuzzle qPuzzle;
	private final PuzzleSolver solver;

	public QuestionPuzzleSolver(QuestionPuzzle qPuzzle) {
		this.qPuzzle = qPuzzle;
		this.solver = new PuzzleSolver(qPuzzle.getPuzzle());
	}

	public List<Attribute> solve() {
		return this.solver.solve().stream().map(solution -> solution.findPerson(qPuzzle.getQuestion()))
				.map(person -> person.findAttribute(qPuzzle.getId())).distinct().collect(Collectors.toList());
	}

	public int countSolutions() {
		return solve().size();
	}

}
