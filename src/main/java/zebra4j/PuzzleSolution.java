package zebra4j;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Value;

@Value
public class PuzzleSolution {

	private final Set<SolutionPerson> people;
	private final Map<AttributeType, Set<Attribute>> attributeSets;

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		people.stream().forEach(p -> builder.append(p));
		return builder.build();
	}
}
