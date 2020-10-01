package zebra4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SolutionPerson {

	private final Map<Class<Literal>, Literal> attributes = new LinkedHashMap<>();

	public SolutionPerson(Literal... attributeList) {
		this(Arrays.asList(attributeList));
	}

	public SolutionPerson(List<Literal> attributeList) {
		for (Literal lit : attributeList) {
			attributes.put(lit.type(), lit);
		}
	}

	public List<Literal> asList() {
		return new ArrayList<>(attributes.values());
	}

	public Set<Class<Literal>> attributeTypes() {
		return Collections.unmodifiableSet(attributes.keySet());
	}

	public SolutionPerson withAttribute(Literal attribute) {
		List<Literal> newAttributes = asList();
		newAttributes.add(attribute);
		return new SolutionPerson(newAttributes);
	}

	@Override
	public String toString() {
		return "SolutionPerson [" + asList() + "]";
	}

}
