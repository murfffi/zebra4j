package zebra4j;

public interface Attribute {

	/**
	 * As in the sentence "Ivan is {description}"
	 */
	String description();

	int asUniqueInt();

	AttributeType type();

	default String typeName() {
		return getClass().getSimpleName();
	}

	default String language() {
		return "bulgarian";
	}
}
