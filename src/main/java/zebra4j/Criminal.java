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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

public enum Criminal implements Attribute {

	NO, YES;

	public static AttributeType TYPE = new AttributeType() {

		@Override
		public void addToModel(ZebraModel zebraModel, Set<Attribute> attributesOfType, int numPeople) {
			Attribute attr = YES;
			Validate.isTrue(attributesOfType.contains(attr));
			zebraModel.createUniqueVariable(attr, numPeople);
		}

		@Override
		public String questionSentencePart() {
			return "Престъпник ли е";
		}

		@Override
		public List<Attribute> getAttributes(int numPeople) {
			Criminal[] set = new Criminal[numPeople];
			Arrays.fill(set, Criminal.NO);
			set[0] = Criminal.YES;
			return Arrays.asList(set);
		}

	};

	@Override
	public String description() {
		return this == NO ? "невинен" : "престъпник";
	}

	@Override
	public AttributeType type() {
		return TYPE;
	}

}
