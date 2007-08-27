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
		
		IMatcher matcher = new RegionMatcher.Builder().addRecognizeSequence("Node Results", 1)
														.addRecognizeSequence("-----------", 2)
														.setEscapeSequence("\n").build();
		assertFalse(matcher.match(text1L1));
		assertFalse(matcher.match(text1L2));
		assertFalse(matcher.match(text1L3));
		assertFalse(matcher.match(text1L4));
		assertFalse(matcher.match(text1L5));
		assertTrue(matcher.match(text1L6));
		assertTrue(matcher.match(text1L7));
		
		matcher = new RegionMatcher.Builder().addRecognizeSequence("Link Results", 1)
												.addRecognizeSequence("-----------", 2)
												.setEscapeSequence("\n").build();
		
		assertFalse(matcher.match(text2L1));
		assertFalse(matcher.match(text2L2));
		assertFalse(matcher.match(text2L3));
		assertFalse(matcher.match(text2L4));
		assertFalse(matcher.match(text2L5));
		assertTrue(matcher.match(text2L6));
		assertTrue(matcher.match(text2L7));
	}
	
	public final void testIllegalMatches(){
		
		IMatcher matcher = new RegionMatcher.Builder().addRecognizeSequence("Node Results", 1).setEscapeSequence("\n").build();
		
		try {
			matcher.match(null);
			fail();
		} catch (IllegalArgumentException e) {}
		
	}
	
	public final void testEquals(){
		
		//this test was created in reason of a bug detected in acceptance test.
		
		RegionMatcher matcher1 = new RegionMatcher.Builder()
								.addRecognizeSequence("Node Results", 1)
								.addRecognizeSequence("-----------", 2)
								.setEscapeSequence("\n").build();
		RegionMatcher matcher2 = new RegionMatcher.Builder()
								.addRecognizeSequence("Link Results", 1)
								.addRecognizeSequence("-----------", 2)
								.setEscapeSequence("\n").build();
		
		assertFalse(matcher1.equals(matcher2));
	}
}
