package org.epanetgrid.util;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public final void testExtraiOrdem() {
	}

	public final void testExtraiAlpha() {
		String P1 = "P1";
		String R10 = "R10";
		
		assertEquals("P", StringUtils.extraiAlpha(P1));
		assertEquals("R", StringUtils.extraiAlpha(R10));
	}

}
