package org.epanetgrid.relatorios.common;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 19/08/2007
 */
public class RegionMatcherTest extends TestCase {

	private final String fooLine = "    ";
	private final String foo2 = "  Page 1                                    Sat Aug 25 17:47:16 2007";

	private final String foo3 = "******************************************************************";
	private final String foo4 = "*                           E P A N E T                          *";
	private final String foo5 = " *                   Hydraulic and Water Quality                  *";
	private final String foo6 = "*                   Analysis for Pipe Networks                   *";
	private final String foo7 = "*                           Version 2.0                          *";
	private final String foo8 = "******************************************************************";


	private final String foo9 = "Input Data File ................... MalhaTeste1-1.inp";
	private final String foo10 = "Number of Junctions................ 17";
	private final String foo11 = "Number of Reservoirs............... 1";
	private final String foo12 = "Number of Tanks ................... 3";
	private final String foo13 = "Number of Pipes ................... 17";
	private final String foo14 = "Number of Pumps ................... 3";
	private final String foo15 = "Number of Valves .................. 0";
	private final String foo16 = "Headloss Formula .................. Hazen-Williams";
	private final String foo17 = "Hydraulic Timestep ................ 1.00 hrs";
	private final String foo18 = "Hydraulic Accuracy ................ 0.001000";
	private final String foo20 = "Maximum Trials .................... 40";
	private final String foo21 = "Quality Analysis .................. None";
	private final String foo22 = "Specific Gravity .................. 1.00";
	private final String foo23 = "Relative Kinematic Viscosity ...... 1.00";
	private final String foo24 = "Relative Chemical Diffusivity ..... 1.00";
	private final String foo25 = "Demand Multiplier ................. 1.00";
	private final String foo26 = "Total Duration .................... 1.00 hrs";
	private final String foo27 = "Reporting Criteria:";
	private final String foo28 = "All Nodes";
	private final String foo29 = "All Links";

	private final String foo30 = "Energy Usage:		";
	private final String foo31 = "----------------------------------------------------------------";
	private final String foo32 = "Usage   Avg.     Kw-hr      Avg.      Peak      Cost";
	private final String foo33 = " Pump      Factor Effic.       /m3        Kw        Kw      /day";
	private final String foo34 = "  ----------------------------------------------------------------";
	private final String foo35 = "  B1        100.05  74.93      0.31     33.54     43.78    158.13";
	private final String foo36 = "  B2        100.05  74.93      0.32     23.13     44.47    108.46";
	private final String foo37 = "  B3        100.05  74.93      0.41     18.72     29.38     84.95";
	private final String foo38 = " ----------------------------------------------------------------";
	private final String foo39 = "                                         Demand Charge:  10651.64";
	private final String foo40 = "                                       Total Cost:     11003.16";



	
	private final String text1L1 = " Node Results at 0:00:00 hrs ";
	private final String text1L2 = " ----------------------------------------------";
	private final String text1L3 = "                    Demand      Head  Pressure";
	private final String text1L4 = " Node                  L/s         m         m";
	private final String text1L5 = " ----------------------------------------------";
	private final String text1L6 = " N1                   0.00    699.94     78.19";
	private final String text1L7 = " R3                  17.37    679.00     10.00  Tank";
	private final String text1L8 = "\n";
	
	private final String text2L1 = " Link Results at 0:00:00 hrs: ";
	private final String text2L2 = " ----------------------------------------------";
	private final String text2L3 = "                     Flow  Velocity  Headloss";
	private final String text2L4 = "Link                  L/s       m/s    /1000m";
	private final String text2L5 = " ----------------------------------------------";
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
		
		
		assertFalse(matcher.match(foo2));
		assertFalse(matcher.match(foo3));
		assertFalse(matcher.match(foo4));
		assertFalse(matcher.match(foo5));
		assertFalse(matcher.match(foo6));
		assertFalse(matcher.match(foo7));
		assertFalse(matcher.match(foo8));
		assertFalse(matcher.match(foo9));
		
		assertFalse(matcher.match(foo10));
		assertFalse(matcher.match(foo11));
		assertFalse(matcher.match(foo12));
		assertFalse(matcher.match(foo13));
		assertFalse(matcher.match(foo14));
		assertFalse(matcher.match(foo15));
		assertFalse(matcher.match(foo16));
		assertFalse(matcher.match(foo17));
		assertFalse(matcher.match(foo18));
		
		assertFalse(matcher.match(foo20));
		assertFalse(matcher.match(foo21));
		assertFalse(matcher.match(foo22));
		assertFalse(matcher.match(foo23));
		assertFalse(matcher.match(foo24));
		assertFalse(matcher.match(foo25));
		assertFalse(matcher.match(foo26));
		assertFalse(matcher.match(foo27));
		assertFalse(matcher.match(foo28));
		assertFalse(matcher.match(foo29));
		
		assertFalse(matcher.match(foo30));
		assertFalse(matcher.match(foo31));
		assertFalse(matcher.match(foo32));
		assertFalse(matcher.match(foo33));
		assertFalse(matcher.match(foo34));
		assertFalse(matcher.match(foo35));
		assertFalse(matcher.match(foo36));
		assertFalse(matcher.match(foo37));
		assertFalse(matcher.match(foo38));
		assertFalse(matcher.match(foo39));
		
		assertFalse(matcher.match(foo40));
		
		assertFalse(matcher.match(fooLine));
		assertFalse(matcher.match(text1L1));
		assertFalse(matcher.match(text1L2));
		assertFalse(matcher.match(text1L3));
		assertFalse(matcher.match(text1L4));
		assertFalse(matcher.match(text1L5));
		assertTrue(matcher.match(text1L6));
		assertTrue(matcher.match(text1L7));
		assertFalse(matcher.match(fooLine));
		
		matcher = new RegionMatcher.Builder().addRecognizeSequence("Link Results", 1)
												.addRecognizeSequence("-----------", 2)
												.setEscapeSequence("\n").build();
		
		assertFalse(matcher.match(fooLine));
		assertFalse(matcher.match(text2L1));
		assertFalse(matcher.match(text2L2));
		assertFalse(matcher.match(text2L3));
		assertFalse(matcher.match(text2L4));
		assertFalse(matcher.match(text2L5));
		assertTrue(matcher.match(text2L6));
		assertTrue(matcher.match(text2L7));
		assertFalse(matcher.match(fooLine));
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
