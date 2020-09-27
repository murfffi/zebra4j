package zebra4j;

import lombok.Value;

@Value
public class AtHouse implements Literal {

	private final int house;

	@Override
	public String description() {
		return "в къща " + house;
	}

}
