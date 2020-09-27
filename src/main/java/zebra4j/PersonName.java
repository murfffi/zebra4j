package zebra4j;

import org.apache.commons.text.CaseUtils;

public enum PersonName implements Literal {

	ИВАН, ЕЛЕНА, ПЕТЪР, ТЕОДОРА, ГЕОРГИ;

	@Override
	public String description() {
		return CaseUtils.toCamelCase(name(), true);
	}

}
