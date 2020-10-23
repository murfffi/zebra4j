package zebra4j;

import java.util.Set;

public interface AttributeType {

	Attribute fromUniqueInt(int input);

	void addToModel(ZebraModel model, Set<Attribute> attributesOfType);

	default boolean checkDifferent() {
		return false;
	}
}
