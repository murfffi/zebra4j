package zebra4j.fact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import zebra4j.Clothes;
import zebra4j.PersonName;
import zebra4j.fact.CommutativeFact.Source;

public interface CommutativeFactUtils {

	public static void testEquals(Source factSource) {
		assertEquals(factSource.create(Clothes.GREEN, PersonName.ELENA),
				factSource.create(PersonName.ELENA, Clothes.GREEN));
		assertNotEquals(factSource.create(Clothes.GREEN, PersonName.ELENA), null);
	}

	public static void testHashcode(Source factSource) {
		assertEquals(factSource.create(Clothes.GREEN, PersonName.ELENA).hashCode(),
				factSource.create(PersonName.ELENA, Clothes.GREEN).hashCode());
	}

}
