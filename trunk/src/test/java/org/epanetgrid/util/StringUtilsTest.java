package org.epanetgrid.util;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class StringUtilsTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public final void testExtraiOrdem() {
		String notOrdinal = "P";
		assertEquals("", StringUtils.extraiOrdem(notOrdinal));
	}

	public final void testExtraiAlpha() {
		String P1 = "P1";
		String R10 = "R10";
		
		assertEquals("P", StringUtils.extraiAlpha(P1));
		assertEquals("R", StringUtils.extraiAlpha(R10));
	}

}
