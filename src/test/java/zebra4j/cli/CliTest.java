/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 - 2021 Marin Nozhchev
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package zebra4j.cli;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import zebra4j.cli.Cli.GenerateCli;

public class CliTest {

	@Test
	public void testGenerateCli_Question() throws IOException {
		GenerateCli command = new GenerateCli();
		command.people = 3;
		Charset enc = StandardCharsets.UTF_8;
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		try {
			command.out = new PrintStream(outs, true, enc.name());
			command.run();
		} finally {
			outs.close();
		}
		assertTrue(new String(outs.toByteArray(), enc).contains("Question"));
	}

}
