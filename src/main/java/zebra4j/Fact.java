package zebra4j;

import java.util.Map;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import lombok.Value;

public interface Fact {

	@Value
	public class BothTrue implements Fact {
		private final Literal left, right;

		@Override
		public String toString() {
			if (left instanceof PersonName) {
				return String.format("%s e %s", left.description(), right.description());
			}
			return String.format("Този който е %s, e и %s.", left.description(), right.description());
		}

		@Override
		public void postTo(Model model, Map<Literal, IntVar> variables) {
			// The person of literal1 is the same as the person of literal2
			model.arithm(variables.get(left), "=", variables.get(right)).post();
		}
	}

	public void postTo(Model model, Map<Literal, IntVar> variables);
}
