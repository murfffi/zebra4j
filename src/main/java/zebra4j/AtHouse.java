package zebra4j;

import lombok.Value;

@Value
public class AtHouse implements Attribute {

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

}
