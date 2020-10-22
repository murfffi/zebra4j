package zebra4j;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public enum Criminal implements Attribute {

	NO, YES;

	public static AttributeType TYPE = new AttributeType() {

		@Override
		public Attribute fromUniqueInt(int input) {
			return input == 1 ? YES : NO;
		}

		@Override
		public void addToModel(ZebraModel zebraModel, Set<Attribute> attributesOfType) {
			Model model = zebraModel.getChocoModel();
			Attribute attr = YES;
			Validate.isTrue(attributesOfType.contains(attr));
			IntVar var = model.intVar(zebraModel.varName(attr), 0, attributesOfType.size() - 1);
			zebraModel.addUniqueVariable(attr, var);
		}

	};

	@Override
	public String description() {
		return this == NO ? "невинен" : "престъпник";
	}

	@Override
	public int asUniqueInt() {
		return ordinal();
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
