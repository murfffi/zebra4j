package zebra4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lombok.AllArgsConstructor;
import zebra4j.Fact.BothTrue;

@AllArgsConstructor
public class PuzzleGenerator {

	private final Random rnd;

	public PuzzleGenerator() {
		this.rnd = new Random();
	}

	public Puzzle generate(PuzzleSolution solution) {
		List<Fact> facts = new ArrayList<>();
		facts.addAll(generateBothTrue(solution));
		if (!hasUniqueSolution(solution, facts)) {
			throw new RuntimeException(String.format("Incomplete rule generation. Puzzle has %s solutions.",
					new PuzzleSolver(toPuzzle(solution, facts)).solve()));
		}
		removeFacts(facts, solution);
		return toPuzzle(solution, facts);
	}

	public Puzzle toPuzzle(PuzzleSolution solution, List<Fact> facts) {
		return new Puzzle(solution.getAttributeSets(), new LinkedHashSet<>(facts));
	}

	private void removeFacts(List<Fact> facts, PuzzleSolution solution) {
		Collections.shuffle(facts, rnd);
		for (int i = 0; i < facts.size(); ++i) {
			List<Fact> factsCopy = new ArrayList<>(facts);
			factsCopy.remove(i);
			if (hasUniqueSolution(solution, factsCopy)) {
				facts.remove(i);
				--i;
			}
		}

	}

	public boolean hasUniqueSolution(PuzzleSolution solution, List<Fact> facts) {
		return new PuzzleSolver(toPuzzle(solution, facts)).countSolutions() == 1;
	}

	public static Set<BothTrue> generateBothTrue(PuzzleSolution solution) {
		Set<BothTrue> result = new LinkedHashSet<>();
		for (SolutionPerson person : solution.getPeople()) {
			List<Attribute> attributes = person.asList();
			for (int i = 0; i < attributes.size(); ++i) {
				for (int j = i + 1; j < attributes.size(); ++j) {
					result.add(new BothTrue(attributes.get(i), attributes.get(j)));
				}
			}
		}
		return result;
	}
}
