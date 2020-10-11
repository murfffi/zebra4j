package zebra4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class PuzzleSolver {

	private final Puzzle puzzle;
	private static final Pattern VAR_REGEX = Pattern.compile("person_of_'(\\w+)'_'([0-9]+)'");

	/**
	 * @return a list of choco-solver solutions; useful to count solutions
	 */
	List<Solution> solveChoco() {
		Map<Literal, IntVar> variables = new HashMap<>();
		Model model = toModel(variables);
		for (Fact fact : puzzle.getFacts()) {
			fact.postTo(model, variables);
		}
		List<Solution> solutions = new ArrayList<>();
		Solver solver = model.getSolver();
		while (solver.solve()) {
			Solution solution = new Solution(model).record();
			solutions.add(solution);
		}
		return solutions;
	}

	public List<PuzzleSolution> solve() {
		List<Solution> solutions = solveChoco();
		return solutions.stream().map(this::fromChocoSolution).distinct().collect(Collectors.toList());
	}

	public int countSolutions() {
		return solve().size();
	}

	private PuzzleSolution fromChocoSolution(Solution choco) {
		int numPeople = puzzle.numPeople();
		@SuppressWarnings("unchecked")
		List<Literal>[] allAttributes = new List[numPeople];
		for (int i = 0; i < allAttributes.length; ++i) {
			allAttributes[i] = new ArrayList<>();
		}
		for (IntVar var : choco.retrieveIntVars(false)) {
			int person = choco.getIntVal(var);
			Literal attribute = toLiteral(var.getName());
			allAttributes[person].add(attribute);
		}
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		Stream.of(allAttributes).forEach(list -> builder.add(new SolutionPerson(list)));
		return builder.build();
	}

	private Model toModel(Map<Literal, IntVar> variables) {
		Model model = new Model();
		for (Set<Literal> attributesOfType : puzzle.getAttributeSets().values()) {
			List<IntVar> varsForType = new ArrayList<>();
			for (Literal attr : attributesOfType) {
				IntVar var = model.intVar(varName(attr), 0, attributesOfType.size() - 1);
				varsForType.add(var);
				variables.put(attr, var);
			}
			// The person for each attribute of a type is different.
			model.allDifferent(varsForType.toArray(new IntVar[0])).post();
		}
		return model;
	}

	private String varName(Literal attr) {
		return String.format("person_of_'%s'_'%s'", attr.typeName(), attr.asUniqueInt());
	}

	@SneakyThrows({ ReflectiveOperationException.class })
	private Literal toLiteral(String name) {
		Matcher m = VAR_REGEX.matcher(name);
		m.matches();
		Class<?> attributeClass = Class.forName(Literal.class.getPackageName() + "." + m.group(1));
		Object result = MethodUtils.invokeStaticMethod(attributeClass, "fromUniqueInt", Integer.parseInt(m.group(2)));
		return (Literal) result;
	}

}
