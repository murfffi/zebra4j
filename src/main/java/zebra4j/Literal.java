package zebra4j;

public interface Literal {

	String description();

	@SuppressWarnings("unchecked")
	default Class<Literal> type() {
		return (Class<Literal>) getClass();
	}

}
