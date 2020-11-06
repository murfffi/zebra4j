package zebra4j;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import lombok.Value;

@Value
public class Question {
	public static Question NAME_OF_CRIMINAL = new Question(Criminal.YES, PersonName.TYPE);

	private final Attribute towards;
	private final AttributeType about;

	public String toSentence() {
		return about.questionSentencePart() + " " + towards.description() + "?";
	}

	public boolean appliesTo(Puzzle puzzle) {
		return appliesTo(puzzle.getAttributeSets());
	}

	public boolean appliesTo(PuzzleSolution solution) {
		return appliesTo(solution.getAttributeSets());
	}

	private boolean appliesTo(Map<AttributeType, Set<Attribute>> attributeSets) {
		boolean towardsApplies = attributeSets.getOrDefault(towards.type(), Collections.emptySet()).contains(towards);
		return towardsApplies && attributeSets.containsKey(about);
	}

}