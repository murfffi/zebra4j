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
import java.util.stream.Collectors;

public class QuestionPuzzleSolver implements CountingSolver {

	private final QuestionPuzzle qPuzzle;
	private final PuzzleSolver solver;

	public QuestionPuzzleSolver(QuestionPuzzle qPuzzle) {
		this.qPuzzle = qPuzzle;
		this.solver = new PuzzleSolver(qPuzzle.getPuzzle());
	}

	public List<Attribute> solve() {
		return this.solver.solve().stream()
				.flatMap(solution -> solution.findPerson(qPuzzle.getQuestion().getTowards()).stream())
				.map(person -> person.findAttribute(qPuzzle.getQuestion().getAbout())).distinct()
				.collect(Collectors.toList());
	}

	@Override
	public int countSolutions() {
		return solve().size();
	}

}
