package org.epanetgrid.perturbador.perturbadores.tank;

import java.util.Random;
import java.util.UUID;

import javax.quantities.Length;
import javax.quantities.Volume;

import org.easymock.EasyMock;
import org.epanetgrid.model.nodes.ITank;
import org.jscience.physics.measures.Measure;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 25/06/2007
 */
public class TankMaximumWaterLevelTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.tank.TankMaximumWaterLevel.disturb(ITank)'
	 */
	public void testDisturbITank() {

		ITank baseTank = EasyMock.createMock(ITank.class);
		
		EasyMock.expect(baseTank.label()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(baseTank.getElevation()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseTank.getInitialWaterLevel()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseTank.getMaximumWaterLevel()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseTank.getMinimumVolume()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Volume.SI_UNIT)).anyTimes();
		EasyMock.expect(baseTank.getMinimumWaterLevel()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseTank.getNominalDiameter()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseTank.getVolumeCurveID()).andReturn(UUID.randomUUID().toString()).anyTimes();
		
		EasyMock.replay(baseTank);
		
		double newValue = new Random().nextDouble();
		ITank tankPert = new TankMaximumWaterLevel(baseTank.label(), newValue).disturb(baseTank);
		
		assertTrue(baseTank.label().equals(tankPert.label()));
		assertTrue(baseTank.getElevation().equals(tankPert.getElevation()));
		assertTrue(baseTank.getInitialWaterLevel().equals(tankPert.getInitialWaterLevel()));
		assertTrue(baseTank.getMinimumVolume().equals(tankPert.getMinimumVolume()));
		assertTrue(baseTank.getMinimumWaterLevel().equals(tankPert.getMinimumWaterLevel()));
		assertTrue(baseTank.getNominalDiameter().equals(tankPert.getNominalDiameter()));
		assertTrue(baseTank.getVolumeCurveID().equals(tankPert.getVolumeCurveID()));
		
		assertEquals(Measure.valueOf(newValue, Length.SI_UNIT).getEstimatedValue(), 
				tankPert.getMaximumWaterLevel().getEstimatedValue());
	}

}
