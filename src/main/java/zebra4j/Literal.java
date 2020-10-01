package zebra4j;

public interface Literal {

	String description();

	int asUniqueInt();

	@SuppressWarnings("unchecked")
	default Class<Literal> type() {
		return (Class<Literal>) getClass();
	}

	default String typeName() {
		return type().getSimpleName();
	}
}
