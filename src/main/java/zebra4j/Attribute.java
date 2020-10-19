package zebra4j;

public interface Attribute {

	String description();

	int asUniqueInt();

	AttributeType type();

	default String typeName() {
		return getClass().getSimpleName();
	}
}
