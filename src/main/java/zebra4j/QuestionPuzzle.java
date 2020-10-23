package zebra4j;

import lombok.Value;

@Value
public class QuestionPuzzle {

	private final Attribute question;
	private final AttributeType id;
	private final Puzzle puzzle;

	public static QuestionPuzzle nameOfCriminal(Puzzle puzzle) {
		return new QuestionPuzzle(Criminal.YES, PersonName.TYPE, puzzle);
	}
}
