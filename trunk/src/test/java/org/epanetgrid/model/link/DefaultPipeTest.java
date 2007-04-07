package org.epanetgrid.model.link;

import java.util.Random;

import javax.quantities.Dimensionless;
import javax.quantities.Length;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class DefaultPipeTest extends TestCase {

	
	public void testBuild(){
		
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> length = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Dimensionless> roughCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		DefaultPipe pipe = new DefaultPipe.Builder("defPipe", EasyMock.createMock(NetWork.class))
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();
		
		assertEquals(diameter, pipe.getDiameter());
		assertEquals(length, pipe.getLength());
		assertEquals(lossCoef, pipe.getLossCoefficient());
		assertEquals(roughCoef, pipe.getRoughnessCoefficient());
	}
}
