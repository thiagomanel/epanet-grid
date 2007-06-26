package org.epanetgrid.perturbador.perturbadores.pipes;

import java.util.Random;
import java.util.UUID;

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.epanetgrid.model.link.IPipe;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 25/06/2007
 */
public class PipeDiameterPerturbadorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.pipes.PipeDiameterPerturbador.disturb(IPipe)'
	 */
	public void testDisturbIPipe() {
		
		IPipe basePipe = EasyMock.createMock(IPipe.class);
		
		EasyMock.expect(basePipe.label()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(basePipe.getDiameter()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(basePipe.getLength()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(basePipe.getLossCoefficient()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Dimensionless.SI_UNIT)).anyTimes();
		EasyMock.expect(basePipe.getRoughnessCoefficient()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Dimensionless.SI_UNIT)).anyTimes();
		
		EasyMock.replay(basePipe);
		
		double newValue = new Random().nextDouble();
		IPipe pipePert = new PipeDiameterPerturbador(basePipe.label(), newValue).disturb(basePipe);
		
		assertTrue(basePipe.label().equals(pipePert.label()));
		assertTrue(basePipe.getLength().equals(pipePert.getLength()));
		assertTrue(basePipe.getLossCoefficient().equals(pipePert.getLossCoefficient()));
		assertTrue(basePipe.getRoughnessCoefficient().equals(pipePert.getRoughnessCoefficient()));
		assertEquals(Measure.valueOf(newValue, VolumetricFlowRate.SI_UNIT).getEstimatedValue(), 
				pipePert.getDiameter().getEstimatedValue());
	}

}
