package zebra4j;

import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

public interface Fact {

	@Value
	@Slf4j
	public class BothTrue implements Fact {
		private final Attribute left, right;

		@Override
		public String toString() {
			if (left instanceof PersonName) {
				return String.format("%s e %s", left.description(), right.description());
			}
			return String.format("Този който е %s, e и %s.", left.description(), right.description());
		}

		@Override
		public void postTo(ZebraModel model) {
			// The person of literal1 is the same as the person of literal2
			IntVar leftVar = model.getVariableFor(left);
			IntVar rightVar = model.getVariableFor(right);

			if (leftVar != null && rightVar != null) {
				Constraint constraint = model.getChocoModel().arithm(leftVar, "=", rightVar);
				log.debug("Adding choco contraint: {}", constraint);
				constraint.post();
			}
		}
	}

	public void postTo(ZebraModel model);
}
