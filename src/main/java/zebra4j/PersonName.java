package zebra4j;

import org.apache.commons.text.CaseUtils;

public enum PersonName implements Attribute {

	ИВАН, ЕЛЕНА, ПЕТЪР, ТЕОДОРА, ГЕОРГИ;

	@Override
	public String description() {
		return CaseUtils.toCamelCase(name(), true);
	}

	@Override
	public int asUniqueInt() {
		return ordinal();
	}

	public static PersonName fromUniqueInt(int input) {
		return PersonName.values()[input];
	}

}
