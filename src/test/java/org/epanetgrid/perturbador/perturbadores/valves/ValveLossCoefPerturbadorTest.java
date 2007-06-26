package org.epanetgrid.perturbador.perturbadores.valves;

import java.util.Random;
import java.util.UUID;

import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.easymock.EasyMock;
import org.epanetgrid.model.link.IValve;
import org.jscience.physics.measures.Measure;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 25/06/2007
 */
public class ValveLossCoefPerturbadorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.valves.ValveLossCoefPerturbador.disturb(IValve)'
	 */
	public void testDisturbIValve() {
		
		IValve baseValve = EasyMock.createMock(IValve.class);
		
		EasyMock.expect(baseValve.label()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(baseValve.getDiameter()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseValve.getLossCoefficient()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		
		EasyMock.replay(baseValve);
		
		double newValue = new Random().nextDouble();
		IValve pipePert = new ValveLossCoefPerturbador(baseValve.label(), newValue).disturb(baseValve);
		
		assertTrue(baseValve.label().equals(pipePert.label()));
		assertTrue(baseValve.getDiameter().equals(pipePert.getDiameter()));
		assertEquals(Measure.valueOf(newValue, VolumetricFlowRate.SI_UNIT).getEstimatedValue(), 
				pipePert.getLossCoefficient().getEstimatedValue());
	}

}
