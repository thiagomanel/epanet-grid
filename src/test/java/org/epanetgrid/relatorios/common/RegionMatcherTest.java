package org.epanetgrid.relatorios.common;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 19/08/2007
 */
public class RegionMatcherTest extends TestCase {

	
	private final String text1 = 

"Node Results at 0:00:00 hrs\n" +
"----------------------------------------------\n" +
"                    Demand      Head  Pressure\n" +
" Node                  L/s         m         m\n" +
"----------------------------------------------\n" +
" N1                   0.00    699.94     78.19\n" +
" N2                   0.53    661.75     28.75\n"+ 
" N3                   0.53    658.58     33.58\n"+
" M1                 -42.77    621.75      0.00  Reservoir\n"+
" R1                  -6.84    663.00     20.00  Tank\n"+
" R2                  22.98    699.00     10.00  Tank\n"+
" R3                  17.37    679.00     10.00  Tank\n\n\n";

	private final String text2 =
		
"Link Results at 0:00:00 hrs:\n" +
"----------------------------------------------\n" +
"                     Flow  Velocity  Headloss\n" +
"Link                  L/s       m/s    /1000m\n" +
"----------------------------------------------\n" +
"T1                  42.77      1.36     15.62\n" +
"T2                  49.61      1.01      6.93\n" +
"T3                  49.08      1.00      6.80\n" +
"T4                  48.55      0.99      6.66\n\n\n";

	/*
	 * Test method for 'org.epanetgrid.relatorios.common.RegionMatcher.match(String)'
	 */
	public final void testMatch() {
		fail();
	}

	public final void testWrongConstructions(){
		
		try {
			IMatcher badMatcher = new RegionMatcher(null);
			fail();
		} catch (IllegalArgumentException e) { }
		 
	}
}
