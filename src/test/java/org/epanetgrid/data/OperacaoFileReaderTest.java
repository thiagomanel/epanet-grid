/**
 * 
 */
package org.epanetgrid.data;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.quantities.Length;
import javax.quantities.Pressure;
import javax.quantities.Velocity;

import org.epanetgrid.model.epanetNetWork.DefaultNetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.ITank;
import org.jscience.physics.measures.Measure;

import junit.framework.TestCase;

/**
 * @author alan
 *
 */
public class OperacaoFileReaderTest extends TestCase {

	private DefaultNetWork network;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		EpaFileReader inpReader = new EpaFileReader();
		this.network = (DefaultNetWork) inpReader.read("resources/accept/MalhaTeste1-1.inp");
	}

	/**
	 * Test method for {@link org.epanetgrid.data.OperacaoFileReader#read(java.lang.String, org.epanetgrid.model.epanetNetWork.DefaultNetWork)}.
	 * @throws IOException 
	 */
	public void testRead() throws IOException {
		
		OperacaoFileReader fileReader = new OperacaoFileReader();
		
		String filePath = "/resources/NoDonutForYou";
		try {
			fileReader.read(filePath, network);
			fail();
		} catch (FileNotFoundException e) { }
		
		filePath = "resources/accept/Operacao.txt";
		fileReader.read(filePath, network);
		
		IJunction<?> n1 = (IJunction<?>) network.getElemento("N1");
		assertTrue(n1.getMaxPressure().approximates(Measure.valueOf(150, Pressure.SI_UNIT)));
		assertTrue(n1.getMinPressure().approximates(Measure.valueOf(10, Pressure.SI_UNIT)));
		
		IJunction<?> n2 = (IJunction<?>) network.getElemento("N2");
		assertTrue(n2.getMaxPressure().approximates(Measure.valueOf(150, Pressure.SI_UNIT)));
		assertTrue(n2.getMinPressure().approximates(Measure.valueOf(10, Pressure.SI_UNIT)));
		
		IJunction<?> n3 = (IJunction<?>) network.getElemento("N3");
		assertTrue(n3.getMaxPressure().approximates(Measure.valueOf(150, Pressure.SI_UNIT)));
		assertTrue(n3.getMinPressure().approximates(Measure.valueOf(10, Pressure.SI_UNIT)));
		
		IJunction<?> n4 = (IJunction<?>) network.getElemento("N4");
		assertTrue(n4.getMaxPressure().approximates(Measure.valueOf(150, Pressure.SI_UNIT)));
		assertTrue(n4.getMinPressure().approximates(Measure.valueOf(10, Pressure.SI_UNIT)));
		
		IPipe<?> t2 = (IPipe<?>) network.getElemento("T2");
		assertTrue(t2.getMaxVelocity().approximates(Measure.valueOf(2, Velocity.SI_UNIT)));
		assertTrue(t2.getMinVelocity().approximates(Measure.valueOf(1, Velocity.SI_UNIT)));
		
		IPipe<?> t3 = (IPipe<?>) network.getElemento("T3");
		assertTrue(t3.getMaxVelocity().approximates(Measure.valueOf(2, Velocity.SI_UNIT)));
		assertTrue(t3.getMinVelocity().approximates(Measure.valueOf(1, Velocity.SI_UNIT)));
		
		IPipe<?> t5 = (IPipe<?>) network.getElemento("T5");
		assertTrue(t5.getMaxVelocity().approximates(Measure.valueOf(3, Velocity.SI_UNIT)));
		assertTrue(t5.getMinVelocity().approximates(Measure.valueOf(1, Velocity.SI_UNIT)));
		
		IPipe<?> t6 = (IPipe<?>) network.getElemento("T6");
		assertTrue(t6.getMaxVelocity().approximates(Measure.valueOf(3, Velocity.SI_UNIT)));
		assertTrue(t6.getMinVelocity().approximates(Measure.valueOf(1, Velocity.SI_UNIT)));
		
		ITank<?> r1 = (ITank<?>) network.getElemento("R1");
		assertTrue(r1.getMaximumSecurityLevel().approximates(Measure.valueOf(2, Length.SI_UNIT)));
		assertTrue(r1.getMinimumSecurityLevel().approximates(Measure.valueOf(1, Length.SI_UNIT)));
		
		ITank<?> r2 = (ITank<?>) network.getElemento("R2");
		assertTrue(r2.getMaximumSecurityLevel().approximates(Measure.valueOf(2, Length.SI_UNIT)));
		assertTrue(r2.getMinimumSecurityLevel().approximates(Measure.valueOf(1, Length.SI_UNIT)));
		
		ITank<?> r3 = (ITank<?>) network.getElemento("R3");
		assertTrue(r3.getMaximumSecurityLevel().approximates(Measure.valueOf(2, Length.SI_UNIT)));
		assertTrue(r3.getMinimumSecurityLevel().approximates(Measure.valueOf(1, Length.SI_UNIT)));
	}

}
