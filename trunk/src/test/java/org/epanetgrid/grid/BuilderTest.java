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

import java.io.File;

import junit.framework.TestCase;

/**
 * @author
 * since 02/09/2007
 */
public class BuilderTest extends TestCase {

	private static final String fs = File.separator;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test method for {@link org.epanetgrid.grid.GridFacade.Builder#Builder(java.lang.String)}.
	 */
	public final void testBuilder() {
	
		try {
			new GridFacade.Builder(null);
			fail();
		} catch (IllegalArgumentException e) { }
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.GridFacade.Builder#build()}.
	 */
	public final void testBuild() {
		
		try {
			new GridFacade.Builder(".").addLibrary("foo").setRequirements("requirements").build();
			fail();
		} catch (IllegalArgumentException e) { }
	
		
		try {
			new GridFacade.Builder(".").addLibrary("resources"+fs+"epanetgrid_source"+fs+"log4j-1.2.8.jar")
			.setRequirements("requirements").addResource(new File("fooResource")).build();
			fail();
		} catch (IllegalArgumentException e) { }
		
		new GridFacade.Builder(".").addLibrary("resources"+fs+"epanetgrid_source"+fs+"log4j-1.2.8.jar").
									setRequirements("requirements").
									addResource(new File("resources"+fs+"epanetgrid_source"+fs+"log4j-1.2.8.jar")).
									build();
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.GridFacade.Builder#addLibrary(java.lang.String)}.
	 */
	public final void testAddLibrary() {
		
		try {
			new GridFacade.Builder("foopath").addLibrary(null);
			fail();
		} catch (IllegalArgumentException e) { }
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.GridFacade.Builder#addResource(java.io.File)}.
	 */
	public final void testAddResource() {

		try {
			new GridFacade.Builder("foopath").addResource(null);
			fail();
		} catch (IllegalArgumentException e) { }
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.GridFacade.Builder#setRequirements(java.lang.String)}.
	 */
	public final void testSetRequirements() {
		
		try {
			new GridFacade.Builder("foopath").setRequirements(null);
			fail();
		} catch (IllegalArgumentException e) { }
	}

}
