package zebra4j;

public interface Attribute {

	String description();

	int asUniqueInt();

	@SuppressWarnings("unchecked")
	default Class<Attribute> type() {
		return (Class<Attribute>) getClass();
	}

	default String typeName() {
		return type().getSimpleName();
	}
}
