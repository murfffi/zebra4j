package zebra4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Value;

@Value
public class PuzzleSolution {

	private final List<SolutionPerson> people;
	private final Map<Class<Literal>, Set<Literal>> attributeSets;
}
