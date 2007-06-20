package org.epanetgrid.perturbador.perturbadores.junctions;

import java.util.Random;
import java.util.UUID;

import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.easymock.EasyMock;
import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.IJunction;
import org.jscience.physics.measures.Measure;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 12/06/2007
 */
public class JunctionBaseDemandFlowPerturbadorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.junctions.JunctionBaseDemandFlowPerturbador.disturb(DefaultJuntion)'
	 */
	public final void testDisturbDefaultJuntion() {

		IJunction baseJunction = EasyMock.createMock(IJunction.class);
		
		EasyMock.expect(baseJunction.label()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(baseJunction.getBaseDemandFlow()).andReturn(Measure.valueOf(new Random().nextDouble(),
				VolumetricFlowRate.SI_UNIT)).anyTimes();
		EasyMock.expect(baseJunction.getElevation()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseJunction.getDemandPatternID()).andReturn(UUID.randomUUID().toString()).anyTimes();
		
		EasyMock.replay(baseJunction);
		
		double newValue = new Random().nextDouble();
		DefaultJuntion junctPert = new JunctionBaseDemandFlowPerturbador(baseJunction.label(), newValue).disturb(baseJunction);
		
		assertTrue(baseJunction.label().equals(junctPert.label()));
		assertTrue(baseJunction.getDemandPatternID().equals(junctPert.getDemandPatternID()));
		assertTrue(baseJunction.getElevation().equals(junctPert.getElevation()));
		assertEquals(Measure.valueOf(newValue, VolumetricFlowRate.SI_UNIT).getEstimatedValue(), 
				junctPert.getBaseDemandFlow().getEstimatedValue());
	}

}
