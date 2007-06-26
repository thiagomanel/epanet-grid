package org.epanetgrid.perturbador.perturbadores.pipes;

import java.util.Random;
import java.util.UUID;

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.easymock.EasyMock;
import org.epanetgrid.model.link.IPipe;
import org.jscience.physics.measures.Measure;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 25/06/2007
 */
public class PipeLengthPerturbadorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.pipes.PipeLengthPerturbador.disturb(DefaultPipe)'
	 */
	public void testDisturbDefaultPipe() {
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
		IPipe pipePert = new PipeLengthPerturbador(basePipe.label(), newValue).disturb(basePipe);
		
		assertTrue(basePipe.label().equals(pipePert.label()));
		assertTrue(basePipe.getDiameter().equals(pipePert.getDiameter()));
		assertTrue(basePipe.getLossCoefficient().equals(pipePert.getLossCoefficient()));
		assertTrue(basePipe.getRoughnessCoefficient().equals(pipePert.getRoughnessCoefficient()));
		assertEquals(Measure.valueOf(newValue, VolumetricFlowRate.SI_UNIT).getEstimatedValue(), 
				pipePert.getLength().getEstimatedValue());
	}

}
