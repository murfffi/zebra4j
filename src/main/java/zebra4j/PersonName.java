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

import java.util.List;
import java.util.Locale;

import org.apache.commons.text.CaseUtils;

public enum PersonName implements Attribute {

	ИВАН, ЕЛЕНА, ПЕТЪР, ТЕОДОРА, ГЕОРГИ;

	public static AttributeType TYPE = new AllDifferentType() {

		@Override
		public String questionSentencePart(Locale locale) {
			return Localization.translate(PersonName.class, "questionSentencePart", locale);
		}

		@Override
		public List<Attribute> getAttributes(int numPeople) {
			return toSolutionSet(PersonName.values(), numPeople);
		}

	};

	@Override
	public String description() {
		return CaseUtils.toCamelCase(name(), true);
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
