package zebra4j;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import lombok.Getter;

public class ZebraModel {

	private static final Pattern VAR_REGEX = Pattern.compile("person_of_'(\\w+)'_'([0-9]+)'");

	@Getter
	private final Model chocoModel = new Model();

	private final Map<String, AttributeType> typeMap = new HashMap<>();

	private Map<Attribute, IntVar> uniqueAttributeVariables = new HashMap<>();

	public void addUniqueVariable(Attribute attr, IntVar var) {
		uniqueAttributeVariables.put(attr, var);
	}

	public IntVar getVariableFor(Attribute uniqueAttribute) {
		return uniqueAttributeVariables.get(uniqueAttribute);
	}

	public String varName(Attribute attr) {
		typeMap.putIfAbsent(attr.typeName(), attr.type());
		return String.format("person_of_'%s'_'%s'", attr.typeName(), attr.asUniqueInt());
	}

	public Attribute toAttribute(String name) {
		Matcher m = VAR_REGEX.matcher(name);
		m.matches();
		int attributeId = Integer.parseInt(m.group(2));
		AttributeType attrType = typeMap.get(m.group(1));
		return attrType.fromUniqueInt(attributeId);
	}
}
