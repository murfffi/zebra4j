/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 Marin Nozhchev
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package zebra4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.chocosolver.solver.DefaultSettings;
import org.chocosolver.solver.IModel;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Settings;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

import lombok.Getter;
import zebra4j.fact.Fact;
import zebra4j.util.LazyInstance;

/**
 * Wrapper on ChocoSolver {@link Model} adding variable management
 * 
 * <p>
 * This is part of the advanced API of zebra4j used when defining new kinds of
 * {@link Fact} or {@link AttributeType}.
 */
@NotThreadSafe
public class ZebraModel {

	private static final Supplier<Settings> DEFAULT_SETTINGS = new LazyInstance<Settings>(DefaultSettings::new);

	/**
	 * The underlying solver {@link Model}
	 * 
	 * <p>
	 * We can't use {@link IModel} here because {@link Solution#Solution} requires
	 * {@link Model}.
	 */
	@Getter
	private final Model chocoModel;

	/**
	 * Initializes a new Zebra ChocoSolver model
	 * 
	 * @param chocoSettings optional, the model uses {@link DefaultSettings} if null
	 */
	public ZebraModel(Settings chocoSettings) {
		chocoModel = new Model(UUID.randomUUID().toString(),
				chocoSettings != null ? chocoSettings : DEFAULT_SETTINGS.get());
	}

	private final BidiMap<Attribute, IntVar> uniqueAttributeVariables = new DualHashBidiMap<Attribute, IntVar>();

	private volatile int varCount = 0;

	/**
	 * Creates a variable for an attribute that can be held by only one person in
	 * the solution
	 * 
	 * <p>
	 * The value of the variable is the index of the person that hold the attribute,
	 * starting at 0. Person indexes start at 0 so the index can be used in a list
	 * or array.
	 * 
	 * @param attr
	 * @param numPeople
	 * @return
	 */
	public IntVar createUniqueVariable(Attribute attr, int numPeople) {
		if (uniqueAttributeVariables.get(attr) != null) {
			throw new IllegalArgumentException(String.format("Variable for attribute %s already exists.", attr));
		}
		IntVar var = chocoModel.intVar(varName(), 0, numPeople - 1);
		uniqueAttributeVariables.put(attr, var);
		return var;
	}

	/**
	 * Finds the variable for the given unique attribute
	 * 
	 * @param uniqueAttribute
	 * @return the variable or null if such variable was not created.
	 */
	public IntVar getVariableFor(Attribute uniqueAttribute) {
		return uniqueAttributeVariables.get(uniqueAttribute);
	}

	/**
	 * Checks if a variable is for person for some attribute
	 * 
	 * <p>
	 * Many variables in a model are not related to people
	 * 
	 * @param var
	 * @return the attribute, present if the variable is for the person that held
	 *         the attribute
	 */
	public Optional<Attribute> toOptionalAttribute(IntVar var) {
		return Optional.ofNullable(uniqueAttributeVariables.getKey(var));
	}

	Map<Attribute, IntVar> getVariableMap() {
		return uniqueAttributeVariables;
	}

	private String varName() {
		return "Person" + ++varCount;
	}
}
