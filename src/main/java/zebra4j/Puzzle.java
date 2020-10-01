package zebra4j;

import java.util.Map;
import java.util.Set;

import lombok.Value;

@Value
public class Puzzle {

	private final Map<Class<Literal>, Set<Literal>> attributeSets;
	private final Set<Fact> facts;

	public int numPeople() {
		return attributeSets.values().iterator().next().size();
	}

}
