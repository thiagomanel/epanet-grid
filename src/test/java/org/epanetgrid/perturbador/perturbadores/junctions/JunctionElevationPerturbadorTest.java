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
 * since 20/06/2007
 */
public class JunctionElevationPerturbadorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.junctions.JunctionElevationPerturbador.disturb(IJunction)'
	 */
	public final void testDisturbIJunction() {
		
		IJunction baseJunction = EasyMock.createMock(IJunction.class);
		
		EasyMock.expect(baseJunction.label()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(baseJunction.getBaseDemandFlow()).andReturn(Measure.valueOf(new Random().nextDouble(),
				VolumetricFlowRate.SI_UNIT)).anyTimes();
		EasyMock.expect(baseJunction.getElevation()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseJunction.getDemandPatternID()).andReturn(UUID.randomUUID().toString()).anyTimes();
		
		EasyMock.replay(baseJunction);
		
		double newValue = new Random().nextDouble();
		DefaultJuntion junctPert = new JunctionElevationPerturbador(baseJunction.label(), newValue).disturb(baseJunction);
		
		assertTrue(baseJunction.label().equals(junctPert.label()));
		assertTrue(baseJunction.getDemandPatternID().equals(junctPert.getDemandPatternID()));
		assertEquals(Measure.valueOf(newValue, Length.SI_UNIT).getEstimatedValue(), 
				junctPert.getElevation().getEstimatedValue());
		assertTrue(baseJunction.getBaseDemandFlow().equals(junctPert.getBaseDemandFlow()));
	}

}
