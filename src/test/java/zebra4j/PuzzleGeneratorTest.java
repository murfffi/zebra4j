package zebra4j;

import org.junit.Test;

public class PuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.ПЕТЪР, Clothes.СИНИ);
		builder.addWithHouse(PersonName.ГЕОРГИ, Clothes.ЖЪЛТИ);
		builder.addWithHouse(PersonName.ИВАН, Clothes.ЗЕЛЕНИ);
		Puzzle generated = PuzzleGenerator.generate(builder.build());
	}

}
