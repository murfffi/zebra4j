package zebra4j;

import lombok.Value;

public interface Fact {

	@Value
	public class BothTrue implements Fact {
		private final Literal left, right;

		@Override
		public String toString() {
			if (left instanceof PersonName) {
				return String.format("%s e %s", left.description(), right.description());
			}
			return String.format("Този който е %s, e и %s.", left.description(), right.description());
		}
	}
}
