package zebra4j;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.Validate;

import zebra4j.fact.Fact;

@NotThreadSafe
public class PuzzleBuilder {

	/**
	 * The attribute types and the sub-sets of their values used in the puzzle.
	 */
	private final Map<AttributeType, Set<Attribute>> attributeSets = new LinkedHashMap<>();

	/**
	 * The facts that must be satisfied by any solution of the puzzle.
	 */
	private final Set<Fact> facts = new LinkedHashSet<>();

	public PuzzleBuilder addSet(Attribute... attributes) {
		Set<AttributeType> types = Stream.of(attributes).map(Attribute::type).collect(Collectors.toSet());
		Validate.isTrue(types.size() == 1, "The attributes must be from the same type but were %s.", types);
		Object result = attributeSets.putIfAbsent(types.iterator().next(), SetUtils.unmodifiableSet(attributes));
		Validate.isTrue(result == null,
				"Multiple sets of the same types are not allowed. Add all values together. Previous set was %s",
				result);
		return this;
	}

	public PuzzleBuilder addSet(String typeName, String... attributeLabels) {
		BasicAttributeType newType = new BasicAttributeType(new LinkedHashSet<>(Arrays.asList(attributeLabels)), "%s",
				typeName);
		attributeSets.put(newType, new LinkedHashSet<>(newType.getAttributes(attributeLabels.length)));
		return this;
	}

	public PuzzleBuilder addFact(Fact fact) {
		facts.add(fact);
		return this;
	}

	public Puzzle build() {
		return new Puzzle(attributeSets, facts);
	}

}
