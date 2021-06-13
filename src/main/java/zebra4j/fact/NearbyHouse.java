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
package zebra4j.fact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import zebra4j.AllDifferentType;
import zebra4j.AtHouse;
import zebra4j.Attribute;
import zebra4j.AttributeType;
import zebra4j.Localization;
import zebra4j.PersonName;
import zebra4j.Puzzle;
import zebra4j.PuzzleSolution;
import zebra4j.SolutionPerson;
import zebra4j.ZebraModel;

/**
 * Facts/clues about people with certain attributes living in adjacent or nearby
 * houses.
 */
@AllArgsConstructor
@ToString
@Getter
@Immutable
public class NearbyHouse extends CommutativeFact {

	private static final int MAX_DISTANCE = 2;

	public static final Type TYPE = new Type() {

		@Override
		public List<Fact> generate(PuzzleSolution solution) {
			List<Fact> result = new ArrayList<>();
			List<SolutionPerson> people = new ArrayList<>(solution.getPeople());
			for (int i = 0; i < people.size(); ++i) {
				SolutionPerson leftPerson = people.get(i);
				AtHouse leftHouse = (AtHouse) leftPerson.findAttribute(AtHouse.TYPE);
				if (leftHouse == null) {
					continue;
				}
				int leftPos = leftHouse.getPosition();
				for (int j = i + 1; j < people.size(); ++j) {
					SolutionPerson rightPerson = people.get(j);
					AtHouse rightHouse = (AtHouse) rightPerson.findAttribute(AtHouse.TYPE);
					if (rightHouse == null) {
						continue;
					}
					int rightPos = rightHouse.getPosition();

					int distance = Math.abs(leftPos - rightPos);

					if (distance > MAX_DISTANCE) {
						continue;
					}

					for (Attribute leftAttr : leftPerson.asList()) {
						Attribute rightAttr = rightPerson.findAttribute(leftAttr.type());
						if (rightAttr != null && leftAttr.type() instanceof AllDifferentType) {
							result.add(new NearbyHouse(distance, leftAttr, rightAttr));
						}
					}
				}
			}
			return result;
		}

		@Override
		public String toString() {
			return NearbyHouse.class.getName();
		}
	};

	private final int distance;
	private final Attribute left;
	private final Attribute right;

	@Override
	public void postTo(ZebraModel model) {
		List<Constraint> constraints = new ArrayList<>();

		int numHouses = countHouses(model);
		IntVar leftAttrVar = model.getVariableFor(left);
		IntVar rightAttrVar = model.getVariableFor(right);
		for (int leftPos = 1; leftPos <= numHouses - distance; ++leftPos) {
			IntVar leftHouseVar = model.getVariableFor(new AtHouse(leftPos));
			int rightPos = leftPos + distance;
			IntVar rightHouseVar = model.getVariableFor(new AtHouse(rightPos));
			Constraint consLeft = model.getChocoModel().arithm(leftHouseVar, "=", leftAttrVar);
			Constraint consRight = model.getChocoModel().arithm(rightHouseVar, "=", rightAttrVar);
			constraints.add(model.getChocoModel().and(consLeft, consRight));

			consLeft = model.getChocoModel().arithm(leftHouseVar, "=", rightAttrVar);
			consRight = model.getChocoModel().arithm(rightHouseVar, "=", leftAttrVar);
			constraints.add(model.getChocoModel().and(consLeft, consRight));
		}

		model.getChocoModel().or(constraints.toArray(new Constraint[0])).post();
	}

	private static int countHouses(ZebraModel model) {
		int result = 0;
		while (model.getVariableFor(new AtHouse(result + 1)) != null) {
			++result;
		}
		return result;
	}

	@Override
	public boolean appliesTo(PuzzleSolution solution) {
		Optional<Integer> leftHousePos = getHousePosition(solution, left);
		Optional<Integer> rightHousePos = getHousePosition(solution, right);
		return leftHousePos.isPresent() && rightHousePos.isPresent()
				&& Math.abs(leftHousePos.get() - rightHousePos.get()) == distance;
	}

	@Override
	public String describe(Locale locale) {
		// https://ell.stackexchange.com/questions/249250/he-lives-two-houses-away-from-me
		// https://hinative.com/en-US/questions/15514466
		Class<?> cls = getClass();
		String distPattern = Localization.translate(cls, "distPattern", locale);
		String dist = distance == 1 ? Localization.translate(cls, "nextDoor", locale)
				: String.format(distPattern, distance);

		String patternId = "genericPattern";
		if (left instanceof PersonName) {
			patternId = "namePattern";
		}

		String pattern = Localization.translate(cls, patternId, locale);
		return String.format(pattern, left.description(locale), dist, right.description(locale));
	}

	/**
	 * Finds the position of the house of the person identified by the given
	 * attribute
	 * 
	 * @param solution
	 * @param attribute
	 * @return
	 */
	private static Optional<Integer> getHousePosition(PuzzleSolution solution, Attribute attribute) {
		return solution.findPerson(attribute).flatMap(person -> Optional.ofNullable(person.findAttribute(AtHouse.TYPE)))
				.map(house -> ((AtHouse) house).getPosition());
	}

	@Override
	public Collection<AttributeType> attributeTypes() {
		return Arrays.asList(left.type(), right.type(), AtHouse.TYPE);
	}

	@Override
	public boolean appliesTo(Puzzle puzzle) {
		return super.appliesTo(puzzle) && puzzle.getAttributeSets().containsKey(AtHouse.TYPE);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((NearbyHouse) obj).distance == this.distance;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ distance;
	}

}
