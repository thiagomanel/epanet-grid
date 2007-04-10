package org.epanetgrid.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.nodes.IJunction;

import junit.framework.TestCase;

/**
 * @author thiago
 *
 */
public class EpaFileReaderTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for 'org.epanetgrid.data.EpaFileReader.read(String)'
	 */
	public final void testRead() throws IOException {
		
		String filePath = "/resources/NoDonutForYou";
		EpaFileReader fileReader = new EpaFileReader();
		
		try {
			fileReader.read(filePath);
			fail();
		} catch (FileNotFoundException e) { }
		
		filePath = "resources/MalhaTeste1-1.inp";
		NetWork netWork = fileReader.read(filePath);
		
		Set<IJunction<?>> junctions  = netWork.getJunctions();
		assertEquals(17, junctions.size());	
	}

}
