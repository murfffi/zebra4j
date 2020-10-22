package zebra4j;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PuzzleGeneratorTest {

	@Test
	public void testGenerate() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.sampleSolution();
		Puzzle puzzle = new PuzzleGenerator().generate(startSolution);
		Collection<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertTrue(result.contains(startSolution));
		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testGenerate_Criminal() {
		PuzzleSolution startSolution = PuzzleGeneratorTest.simpleSolutionWithCriminal();
		Puzzle puzzle = new PuzzleGenerator().generate(startSolution);
		log.debug("Puzzle is {}", puzzle);
		Collection<PuzzleSolution> result = new PuzzleSolver(puzzle).solve();
		Assert.assertEquals(1, result.size());
		PuzzleSolution solution = result.iterator().next();
		Assert.assertEquals(solution.toString(), 2, solution.getPeople().size());
		Assert.assertTrue(solution.getPeople().contains(startSolution.findPerson(Criminal.YES)));
	}

	public static PuzzleSolution sampleSolution() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.ПЕТЪР, Clothes.СИНИ);
		builder.addWithHouse(PersonName.ГЕОРГИ, Clothes.ЖЪЛТИ);
		builder.addWithHouse(PersonName.ИВАН, Clothes.ЗЕЛЕНИ);
		PuzzleSolution solution = builder.build();
		return solution;
	}

	public static PuzzleSolution simpleSolutionWithCriminal() {
		PuzzleSolutionBuilder builder = new PuzzleSolutionBuilder();
		builder.addWithHouse(PersonName.ПЕТЪР, Criminal.NO);
		builder.addWithHouse(PersonName.ГЕОРГИ, Criminal.YES);
		PuzzleSolution startSolution = builder.build();
		return startSolution;
	}

}
