package zebra4j;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import zebra4j.Fact.BothTrue;

public class PuzzleGenerator {

	public static Puzzle generate(PuzzleSolution solution) {
		Set<Fact> facts = new LinkedHashSet<>();
		facts.addAll(generateBothTrue(solution));
		return new Puzzle(solution.getAttributeSets(), facts);
	}

	public static Set<BothTrue> generateBothTrue(PuzzleSolution solution) {
		Set<BothTrue> result = new LinkedHashSet<>();
		for (SolutionPerson person : solution.getPeople()) {
			List<Literal> attributes = person.asList();
			for (int i = 0; i < attributes.size(); ++i) {
				for (int j = i + 1; j < attributes.size(); ++j) {
					result.add(new BothTrue(attributes.get(i), attributes.get(j)));
				}
			}
		}
		return result;
	}
}
