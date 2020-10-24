/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 Marin Nozhchev
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package zebra4j;

import org.apache.commons.text.CaseUtils;

public enum PersonName implements Attribute {

	ИВАН, ЕЛЕНА, ПЕТЪР, ТЕОДОРА, ГЕОРГИ;

	public static AttributeType TYPE = new AllDifferentType() {

		@Override
		public Attribute fromUniqueInt(int input) {
			return PersonName.fromUniqueInt(input);
		}

		@Override
		public String questionSentencePart() {
			return "Кой е";
		}

	};

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

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
