package org.epanetgrid.perturbador.perturbadores.reservoir;

import java.util.Random;
import java.util.UUID;

import javax.quantities.Length;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.epanetgrid.model.nodes.IReservoir;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 25/06/2007
 */
public class ReservoirHeadPerturbadorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.perturbador.perturbadores.reservoir.ReservoirHeadPerturbador.disturb(IReservoir)'
	 */
	public void testDisturbIReservoir() {
		
		IReservoir baseReservoir = EasyMock.createMock(IReservoir.class);
		
		EasyMock.expect(baseReservoir.label()).andReturn(UUID.randomUUID().toString()).anyTimes();
		EasyMock.expect(baseReservoir.getHead()).andReturn(Measure.valueOf(new Random().nextDouble(),
				Length.SI_UNIT)).anyTimes();
		EasyMock.expect(baseReservoir.getHeadPatternID()).andReturn(UUID.randomUUID().toString()).anyTimes();
		
		EasyMock.replay(baseReservoir);
		
		double newValue = new Random().nextDouble();
		IReservoir reservoirPert = new ReservoirHeadPerturbador(baseReservoir.label(), newValue).disturb(baseReservoir);
		
		assertTrue(baseReservoir.label().equals(reservoirPert.label()));
		assertTrue(baseReservoir.getHeadPatternID().equals(reservoirPert.getHeadPatternID()));
		assertEquals(Measure.valueOf(newValue, Length.SI_UNIT).getEstimatedValue(), 
				reservoirPert.getHead().getEstimatedValue());
	}

}
