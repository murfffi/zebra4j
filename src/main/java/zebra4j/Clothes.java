package zebra4j;

public enum Clothes implements Attribute {

	ЧЕРВЕНИ, СИНИ, ЗЕЛЕНИ, ЖЪЛТИ;

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

}
