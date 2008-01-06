package org.epanetgrid.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.joda.time.Duration;

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
	@SuppressWarnings("unchecked")
	public final void testRead() throws IOException {
		
		String filePath = "/resources/NoDonutForYou";
		EpaFileReader fileReader = new EpaFileReader();
		
		try {
			fileReader.read(filePath);
			fail();
		} catch (FileNotFoundException e) { }
		
		filePath = "resources/accept/MalhaTeste1-1.inp";
		NetWork netWork = fileReader.read(filePath);
		
		Set<IJunction<?>> junctions  = netWork.getJunctions();
		assertEquals(17, junctions.size());	
		
		Set<IReservoir> reservoirs = netWork.getReservoirs();
		assertEquals(1, reservoirs.size());
		
		Set<ITank<?>> tanks = netWork.getTanks();
		assertEquals(3, tanks.size());
		
		Set<IPipe> pipes = netWork.getPipes();
		assertEquals(17, pipes.size());
		
		Set<ITank<?>> pumps = netWork.getPumps();
		assertEquals(3, pumps.size());
		
		Set<String> options = netWork.getOptions();
		assertEquals(2, options.size());
		
		Set<String> patterns = netWork.getPattern();
		assertEquals(2, patterns.size());
		
		Set<String> energy = netWork.getEnergy();
		assertEquals(3, energy.size());
		
		Set<String> times = netWork.getTimes();
		assertEquals(4, times.size());
		
		Map<String, String> reports = netWork.getReports().getValues();
		assertEquals(7, reports.size());
		
		Map<String, Map<Integer, Boolean>> controls = netWork.getControls();
		
		assertEquals(1, controls.size());
		assertEquals(2, controls.get("B1").size());
		assertFalse(controls.get("B1").get(1));
		assertTrue(controls.get("B1").get(10));
		
		assertEquals(new Duration(24 * 60 * 60 * 1000), netWork.getDuration());
		
		assertEquals(new Duration(60 * 60 * 1000), netWork.getHydraulicTimestep());
		
		assertTrue(reports.containsKey("FILE"));

		assertEquals("Relatorio-1.txt" , netWork.getReports().getValue("FILE").trim());
	}

}
