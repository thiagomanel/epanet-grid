package org.epanetgrid.relatorios.common;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 19/08/2007
 */
public class RegionMatcherTest extends TestCase {

	private final String text1L1 = "Node Results at 0:00:00 hrs";
	private final String text1L2 = "----------------------------------------------";
	private final String text1L3 = "                    Demand      Head  Pressure";
	private final String text1L4 = " Node                  L/s         m         m";
	private final String text1L5 = "----------------------------------------------";
	private final String text1L6 = " N1                   0.00    699.94     78.19";
	private final String text1L7 = " R3                  17.37    679.00     10.00  Tank";
	private final String text1L8 = "\n";
	
	private final String text2L1 = "Link Results at 0:00:00 hrs:";
	private final String text2L2 = "----------------------------------------------";
	private final String text2L3 = "                     Flow  Velocity  Headloss";
	private final String text2L4 = "Link                  L/s       m/s    /1000m";
	private final String text2L5 = "----------------------------------------------";
	private final String text2L6 = "T1                  42.77      1.36     15.62";
	private final String text2L7 = "T4                  48.55      0.99      6.66";
	private final String text2L8 = "\n";

	/*
	 * Test method for 'org.epanetgrid.relatorios.common.RegionMatcher.match(String)'
	 */
	public final void testMatch() {
		
		IMatcher matcher = new RegionMatcher("Node Results", "\n");
		assertTrue(matcher.match(text1L1));
		assertTrue(matcher.match(text1L2));
		assertTrue(matcher.match(text1L3));
		assertTrue(matcher.match(text1L4));
		assertTrue(matcher.match(text1L5));
		assertTrue(matcher.match(text1L6));
		assertTrue(matcher.match(text1L7));
		assertFalse(matcher.match(text1L8));
		
		matcher = new RegionMatcher("Link Results", "\n");
		assertTrue(matcher.match(text2L1));
		assertTrue(matcher.match(text2L2));
		assertTrue(matcher.match(text2L3));
		assertTrue(matcher.match(text2L4));
		assertTrue(matcher.match(text2L5));
		assertTrue(matcher.match(text2L6));
		assertTrue(matcher.match(text2L7));
		assertFalse(matcher.match(text2L8));
		
		matcher = new RegionMatcher("Node Results", "\n");
		assertTrue(matcher.match(text1L1));
		assertTrue(matcher.match(text1L2));
		assertTrue(matcher.match(text1L3));
		assertTrue(matcher.match(text1L4));
		assertTrue(matcher.match(text1L5));
		assertTrue(matcher.match(text1L6));
		assertTrue(matcher.match(text1L7));
		assertFalse(matcher.match(text1L8));
		assertFalse(matcher.match(text2L1));
		assertFalse(matcher.match(text2L2));
		assertFalse(matcher.match(text2L3));
		assertFalse(matcher.match(text2L4));
		assertFalse(matcher.match(text2L5));
		assertFalse(matcher.match(text2L6));
		assertFalse(matcher.match(text2L7));
		assertFalse(matcher.match(text2L8));
		
	}
	
	public final void testIllegalMatches(){
		
		IMatcher matcher = new RegionMatcher("foo result", "\n");
		
		try {
			matcher.match(null);
			fail();
		} catch (IllegalArgumentException e) {}
		
	}

	public final void testWrongConstructions(){
		
		try {
			new RegionMatcher(null, "\n");
			fail();
		} catch (IllegalArgumentException e) { }
	}
}
