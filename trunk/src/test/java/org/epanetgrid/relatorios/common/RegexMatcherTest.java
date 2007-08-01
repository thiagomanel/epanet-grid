/**
 * 
 */
package org.epanetgrid.relatorios.common;

import org.epanetgrid.relatorios.common.RegexMatcher;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public class RegexMatcherTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * 
	 */
	public void testWrongConstructions(){
		
		try {
			new RegexMatcher(null);
			fail();
		} catch (IllegalArgumentException e) { }
		
	}
	
	/**
	 * 
	 */
	public void testInvalidParameters(){
		
		try {
			new RegexMatcher("foo").match(null);
			fail();
		} catch (IllegalArgumentException e) { }
	}
	
	/**
	 * 
	 */
	public void testMatch(){
		
		assertTrue(new RegexMatcher("foo").match("foo"));
		
		assertFalse(new RegexMatcher("foo").match(" foo"));
		assertFalse(new RegexMatcher("foo").match("foo "));
		assertFalse(new RegexMatcher("foo").match(" foo "));
		
		assertTrue(new RegexMatcher(".foo").match(" foo"));
		assertTrue(new RegexMatcher(".foo").match("afoo"));
		
	}
	
}
