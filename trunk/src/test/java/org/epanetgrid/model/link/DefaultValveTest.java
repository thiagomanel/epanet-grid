package org.epanetgrid.model.link;

import java.util.Random;

import javax.quantities.Dimensionless;
import javax.quantities.Length;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class DefaultValveTest extends TestCase {

	public void testBuild(){
		
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		DefaultValve valve = new DefaultValve.Builder("defValve", EasyMock.createMock(NetWork.class))
		.lossCoefficient(lossCoef)
		.diameter(diameter)
		.build();
		
		assertEquals(diameter, valve.getDiameter());
		assertEquals(lossCoef, valve.getLossCoefficient());
	}
}
