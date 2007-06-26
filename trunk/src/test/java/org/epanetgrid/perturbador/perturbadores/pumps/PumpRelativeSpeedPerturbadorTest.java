package org.epanetgrid.perturbador.perturbadores.pumps;

import java.util.Random;
import java.util.UUID;

import javax.quantities.Energy;
import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.easymock.EasyMock;
import org.epanetgrid.model.link.IPump;
import org.jscience.physics.measures.Measure;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 25/06/2007
 */
public class PumpRelativeSpeedPerturbadorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.pumps.PumpRelativeSpeedPerturbador.disturb(IPump)'
	 */
	public void testDisturbIPump() {

		IPump basePump = EasyMock.createMock(IPump.class);
		
		EasyMock.expect(basePump.label()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(basePump.getPower()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Energy.SI_UNIT)).anyTimes();
		EasyMock.expect(basePump.getRelativeSpeed()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(basePump.getHeadCurveID()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(basePump.idTimePattern()).andReturn(UUID.randomUUID().toString()).anyTimes();
		
		EasyMock.replay(basePump);
		
		double newValue = new Random().nextDouble();
		IPump pumpPert = new PumpRelativeSpeedPerturbador(basePump.label(), newValue).disturb(basePump);
		
		assertTrue(basePump.label().equals(pumpPert.label()));
		assertTrue(basePump.getPower().equals(pumpPert.getPower()));
		assertTrue(basePump.getHeadCurveID().equals(pumpPert.getHeadCurveID()));
		assertTrue(basePump.idTimePattern().equals(pumpPert.idTimePattern()));
		assertEquals(Measure.valueOf(newValue, VolumetricFlowRate.SI_UNIT).getEstimatedValue(), 
				pumpPert.getRelativeSpeed().getEstimatedValue());
	}

}
