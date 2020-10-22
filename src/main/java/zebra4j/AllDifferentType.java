package zebra4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public abstract class AllDifferentType implements AttributeType {

	@Override
	public void addToModel(ZebraModel zebraModel, Set<Attribute> attributesOfType) {
		Model model = zebraModel.getChocoModel();
		List<IntVar> varsForType = new ArrayList<>();
		for (Attribute attr : attributesOfType) {
			IntVar var = model.intVar(zebraModel.varName(attr), 0, attributesOfType.size() - 1);
			varsForType.add(var);
			zebraModel.addUniqueVariable(attr, var);
		}
		// The person for each attribute of a type is different.
		model.allDifferent(varsForType.toArray(new IntVar[0])).post();
	}
}
