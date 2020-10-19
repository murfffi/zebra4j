package zebra4j;

import lombok.Value;

@Value
public class Criminal implements Attribute {

	private final boolean isCriminal;

	public static AttributeType TYPE = new AttributeType() {

		@Override
		public Attribute fromUniqueInt(int input) {
			return new Criminal(input == 1);
		}

	};

	@Override
	public String description() {
		return "е престъпник";
	}

	@Override
	public int asUniqueInt() {
		return isCriminal ? 1 : 0;
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
