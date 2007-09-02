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
package org.epanetgrid.data;

import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 01/09/2007
 */
public class DataFacadeTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link org.epanetgrid.data.DataFacade#createNetWorkFromFile(java.lang.String)}.
	 */
	public final void testCreateNetWorkFromFile() {
		
		try {
			new DataFacade().createNetWorkFromFile("fOO-filw");
			fail();
		} catch (IOException e) { }
	}

	/**
	 * Test method for {@link org.epanetgrid.data.DataFacade#saveNetWork(org.epanetgrid.model.epanetNetWork.NetWork, java.lang.String)}.
	 * @throws IOException 
	 */
	public final void testSaveNetWork() throws IOException {
		try {
			new DataFacade().saveNetWork(null, "fOO-filw");
			fail();
		} catch (IllegalArgumentException e) { }
	}

}
