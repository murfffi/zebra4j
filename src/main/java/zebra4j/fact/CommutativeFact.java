package zebra4j.fact;

import zebra4j.Attribute;

abstract class CommutativeFact implements Fact {

	public abstract Attribute getLeft();

	public abstract Attribute getRight();

	@Override
	public boolean equals(Object b) {
		if (!(getClass().isInstance(b))) {
			return false;
		}
		CommutativeFact other = (CommutativeFact) b;
		return getLeft().equals(other.getLeft()) && getRight().equals(other.getRight())
				|| getRight().equals(other.getLeft()) && getLeft().equals(other.getRight());
	}

	@Override
	public int hashCode() {
		return getLeft().hashCode() ^ getRight().hashCode();
	}

	@FunctionalInterface
	interface Source {
		CommutativeFact create(Attribute left, Attribute right);
	}
}
