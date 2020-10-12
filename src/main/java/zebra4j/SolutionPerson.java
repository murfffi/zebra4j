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

	private final Map<Class<Attribute>, Attribute> attributes = new LinkedHashMap<>();

	public SolutionPerson(Attribute... attributeList) {
		this(Arrays.asList(attributeList));
	}

	public SolutionPerson(List<Attribute> attributeList) {
		for (Attribute lit : attributeList) {
			attributes.put(lit.type(), lit);
		}
	}

	public List<Attribute> asList() {
		return new ArrayList<>(attributes.values());
	}

	public Set<Class<Attribute>> attributeTypes() {
		return Collections.unmodifiableSet(attributes.keySet());
	}

	public SolutionPerson withAttribute(Attribute attribute) {
		List<Attribute> newAttributes = asList();
		newAttributes.add(attribute);
		return new SolutionPerson(newAttributes);
	}

	@Override
	public String toString() {
		return "SolutionPerson [" + asList() + "]";
	}

}
