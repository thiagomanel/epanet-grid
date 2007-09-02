/*
 * Copyright (c) 2002-2007 Universidade Federal de Campina Grande This program
 * is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.epanetgrid.grid;

import java.util.Collection;
import java.util.LinkedList;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 02/09/2007
 */
public class EpanetGridRunnableResultTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.EpanetGridRunnableResult#EpanetGridRunnableResult(java.lang.String, java.util.List, java.util.List, java.util.List, java.lang.String, java.util.List, int)}.
	 */
	public final void testEpanetGridRunnableResult() {

		try {
			new EpanetGridRunnableResult(null, new LinkedList<String>(), new LinkedList<String>(), new LinkedList<String>(), 
					"foo", new LinkedList<String>(), 1);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			new EpanetGridRunnableResult("foo", null, new LinkedList<String>(), new LinkedList<String>(), 
					"foo", new LinkedList<String>(), 1);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			new EpanetGridRunnableResult("foo", new LinkedList<String>(), null, new LinkedList<String>(), 
					"foo", new LinkedList<String>(), 1);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			new EpanetGridRunnableResult("foo", new LinkedList<String>(), new LinkedList<String>(), null, 
					"foo", new LinkedList<String>(), 1);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			new EpanetGridRunnableResult("foo", new LinkedList<String>(), new LinkedList<String>(), new LinkedList<String>(), 
					null, new LinkedList<String>(), 1);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			new EpanetGridRunnableResult("foo", new LinkedList<String>(), new LinkedList<String>(), new LinkedList<String>(), 
					"foo", null, 1);
			fail();
		} catch (IllegalArgumentException e) { }
		
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.EpanetGridRunnableResult#getLogMessage()}.
	 */
	public final void testGetLogMessage() {
		
		EpanetGridRunnableResult result = new EpanetGridRunnableResult("foo", new LinkedList<String>(), new LinkedList<String>(), new LinkedList<String>(), 
				"foo", new LinkedList<String>(), 1);
		
		assertTrue(isUnmodifiable(result.getLogMessage()));
	}

	
	/**
	 * Test method for {@link org.epanetgrid.grid.EpanetGridRunnableResult#getErrorMessage()}.
	 */
	public final void testGetErrorMessage() {
		
		EpanetGridRunnableResult result = new EpanetGridRunnableResult("foo", new LinkedList<String>(), new LinkedList<String>(), new LinkedList<String>(), 
				"foo", new LinkedList<String>(), 1);
		
		assertTrue(isUnmodifiable(result.getErrorMessage()));
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.EpanetGridRunnableResult#getConteudoArquivo()}.
	 */
	public final void testGetConteudoArquivo() {
		EpanetGridRunnableResult result = new EpanetGridRunnableResult("foo", new LinkedList<String>(), new LinkedList<String>(), new LinkedList<String>(), 
				"foo", new LinkedList<String>(), 1);
		
		assertTrue(isUnmodifiable(result.getConteudoArquivo()));
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.EpanetGridRunnableResult#getSaidaMessage()}.
	 */
	public final void testGetSaidaMessage() {
		EpanetGridRunnableResult result = new EpanetGridRunnableResult("foo", new LinkedList<String>(), new LinkedList<String>(), new LinkedList<String>(), 
				"foo", new LinkedList<String>(), 1);
		
		assertTrue(isUnmodifiable(result.getSaidaMessage()));
	}


	private final boolean isUnmodifiable(Collection testedCollection) {
		
		try {
			testedCollection.add(new Object());
		} catch (UnsupportedOperationException e) {
			return false;
		}
		
		try {
			testedCollection.remove(new Object());
		} catch (UnsupportedOperationException e) {
			return false;
		}
		
		try {
			testedCollection.addAll(new LinkedList<Object>());
		} catch (UnsupportedOperationException e) {
			return false;
		}
		
		try {
			testedCollection.removeAll(new LinkedList<Object>());
		} catch (UnsupportedOperationException e) {
			return false;
		}
		
		try {
			testedCollection.retainAll(new LinkedList<Object>());
		} catch (UnsupportedOperationException e) {
			return false;
		}
		
		try {
			testedCollection.clear();
		} catch (UnsupportedOperationException e) {
			return false;
		}
		
		return true;
	}
}