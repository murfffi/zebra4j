package zebra4j;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Puzzle {

	private final Map<AttributeType, Set<Attribute>> attributeSets;
	private final Set<Fact> facts;

	public int numPeople() {
		return attributeSets.values().iterator().next().size();
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		attributeSets.entrySet().stream().forEach(p -> builder.append(p));
		facts.stream().forEach(p -> builder.append(p));
		return builder.build();
	}
}
