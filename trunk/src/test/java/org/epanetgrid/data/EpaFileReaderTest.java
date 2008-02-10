package org.epanetgrid.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IValve;
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
		
		assertEquals(2, controls.size());
		assertEquals(1, controls.get(1).size());
		assertNull(controls.get(2));
		assertEquals(1, controls.get(10).size());
		assertFalse(controls.get(1).get("B1"));
		assertTrue(controls.get(10).get("B1"));
		
		assertEquals(new Duration(24 * 60 * 60 * 1000), netWork.getDuration());
		
		assertEquals(new Duration(60 * 60 * 1000), netWork.getHydraulicTimestep());
		
		assertTrue(reports.containsKey("FILE"));

		assertEquals("Relatorio-1.txt" , netWork.getReports().getValue("FILE").trim());
	}
	
	public final void testRead2() throws IOException {
		
		EpaFileReader fileReader = new EpaFileReader();
		
		String filePath = "resources/accept/RedeSerea.inp";
		NetWork netWork = fileReader.read(filePath);
		
		Set<IJunction<?>> junctions  = netWork.getJunctions();
		assertEquals(23, junctions.size());	
		
		Set<IReservoir> reservoirs = netWork.getReservoirs();
		assertEquals(1, reservoirs.size());
		
		Set<ITank<?>> tanks = netWork.getTanks();
		assertEquals(4, tanks.size());
		
		Set<IPipe> pipes = netWork.getPipes();
		assertEquals(26, pipes.size());
		
		Set<ITank<?>> pumps = netWork.getPumps();
		assertEquals(5, pumps.size());
		
		Set<IValve<?>> valves = netWork.getValves();
		assertEquals(1, valves.size());

		Set<String> tags = netWork.getTags();
		assertEquals(0, tags.size());

		Set<String> demands = netWork.getDemands();
		assertEquals(0, demands.size());

		Set<String> status = netWork.getStatus();
		assertEquals(7, status.size());
		
		Set<String> patterns = netWork.getPattern();
		assertEquals(16, patterns.size());
		
		Set<String> curves = (Set<String>) netWork.getCurves();
		assertEquals(49, curves.size());
		
		Map<String, Map<Integer, Boolean>> controls = netWork.getControls();
		assertEquals(0, controls.size());

		Set<String> rules = netWork.getRules();
		assertEquals(96, rules.size());
		
		Set<String> energy = netWork.getEnergy();
		assertEquals(9, energy.size());
		
		Set<String> emmiters = netWork.getEmmiters();
		assertEquals(0, emmiters.size());
		
		Set<String> quality = netWork.getQuality();
		assertEquals(0, quality.size());
		
		Set<String> sources = netWork.getSources();
		assertEquals(0, sources.size());
		
		Set<String> reactions = netWork.getReactions();
		assertEquals(6, reactions.size());

		Set<String> mixing = netWork.getMixing();
		assertEquals(0, mixing.size());

		Set<String> times = netWork.getTimes();
		assertEquals(9, times.size());
		
		Set<String> options = netWork.getOptions();
		assertEquals(13, options.size());
		
		Set<String> coordinates = netWork.getCoordinates();
		assertEquals(28, coordinates.size());
		
		Set<String> vertices = netWork.getVertices();
		assertEquals(4, vertices.size());
		
		Set<String> labels = netWork.getLabels();
		assertEquals(0, labels.size());
		
		Set<String> backdrop = netWork.getBackdrop();
		assertEquals(4, backdrop.size());
		
		Map<String, String> reports = netWork.getReports().getValues();
		assertEquals(7, reports.size());
		
		assertTrue(reports.containsKey("FILE"));

		assertEquals("resultado_serea.txt" , netWork.getReports().getValue("FILE").trim());
	}
	

}
