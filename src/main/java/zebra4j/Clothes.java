package zebra4j;

public enum Clothes implements Attribute {

	ЧЕРВЕНИ, СИНИ, ЗЕЛЕНИ, ЖЪЛТИ;

	public static AttributeType TYPE = new AttributeType() {

		@Override
		public Attribute fromUniqueInt(int input) {
			return Clothes.fromUniqueInt(input);
		}

	};

	/**
	 * As in the sentence "Иван е {description}"
	 */
	@Override
	public String description() {
		return String.format("с %s дрехи", name().toLowerCase());
	}

	@Override
	public int asUniqueInt() {
		return ordinal();
	}

	public static Clothes fromUniqueInt(int input) {
		return Clothes.values()[input];
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
