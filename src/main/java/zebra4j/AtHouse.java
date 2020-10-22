package zebra4j;

import lombok.Value;

@Value
public class AtHouse implements Attribute {

	public static AttributeType TYPE = new AllDifferentType() {

		@Override
		public Attribute fromUniqueInt(int input) {
			return AtHouse.fromUniqueInt(input);
		}

	};

	private final int house;

	@Override
	public String description() {
		return "в къща " + house;
	}

	@Override
	public int asUniqueInt() {
		return house;
	}

	public static AtHouse fromUniqueInt(int input) {
		return new AtHouse(input);
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
