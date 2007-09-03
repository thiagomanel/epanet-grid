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
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 02/09/2007
 */
public class GridFacadeTest extends TestCase {

	private static final String fs = File.separator;
	private GridFacade facade;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		facade = new GridFacade.Builder(".").addLibrary("resources"+fs+"epanetgrid_source"+fs+"log4j-1.2.8.jar").
		setRequirements("requirements").
		addResource(new File("resources"+fs+"epanetgrid_source"+fs+"log4j-1.2.8.jar")).
		build();
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.GridFacade#addNetWorkFile(java.io.File, java.lang.String)}.
	 */
	public final void testAddNetWorkFileFileString() {
		
		File file = null;
		
		try {
			facade.addNetWorkFile(file, null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			facade.addNetWorkFile(file, "foo");
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			facade.addNetWorkFile(new File("foo"), null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		File goodFile = new File("resources"+fs+"epanetgrid_source"+fs+"log4j-1.2.8.jar");
		facade.addNetWorkFile(goodFile, "foo");
		
		facade.addNetWorkFile(goodFile, "not again");
	}

	/**
	 * Test method for {@link org.epanetgrid.grid.GridFacade#addNetWorkFile(java.lang.String, java.lang.String)}.
	 */
	public final void testAddNetWorkFileStringString() {
		
		String firstString = null;
		
		try {
			facade.addNetWorkFile(firstString, null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			facade.addNetWorkFile("!firstString", null);
			fail();
		} catch (IllegalArgumentException e) { }
	}
}
