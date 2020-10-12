package zebra4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class PuzzleSolutionBuilder {

	private final List<SolutionPerson> people = new ArrayList<>();

	private Map<Class<Attribute>, Set<Attribute>> attributeSets = new LinkedHashMap<>();

	public PuzzleSolutionBuilder add(Attribute... attributes) {
		return add(new SolutionPerson(attributes));
	}

	public PuzzleSolutionBuilder add(SolutionPerson person) {
		if (!attributeSets.isEmpty() && !getAttributeTypes().equals(person.attributeTypes())) {
			throw new IllegalArgumentException("People must have the same attributes.");
		}
		addAttributes(person.asList());

		people.add(person);
		return this;
	}

	private void addAttributes(List<Attribute> attributes) {
		for (Attribute attr : attributes) {
			attributeSets.putIfAbsent(attr.type(), new LinkedHashSet<>());
			boolean added = attributeSets.get(attr.type()).add(attr);
			if (!added) {
				throw new IllegalArgumentException("Attributes must be different.");
			}
		}

	}

	private Set<Class<Attribute>> getAttributeTypes() {
		return attributeSets.keySet();
	}

	public PuzzleSolutionBuilder addWithHouse(Attribute... attributes) {
		return addWithHouse(new SolutionPerson(attributes));
	}

	public PuzzleSolutionBuilder addWithHouse(SolutionPerson person) {
		if (person.attributeTypes().contains(AtHouse.class)) {
			throw new IllegalArgumentException("AtHouse is not allowed to be pre-set in this case.");
		}
		SolutionPerson personWithHouse = person.withAttribute(new AtHouse(people.size() + 1));
		return add(personWithHouse);
	}

	public PuzzleSolution build() {
		return new PuzzleSolution(new LinkedHashSet<>(people), attributeSets);
	}

}
