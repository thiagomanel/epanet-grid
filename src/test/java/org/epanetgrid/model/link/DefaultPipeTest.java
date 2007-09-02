package org.epanetgrid.model.link;

import java.util.Random;

import javax.quantities.Dimensionless;
import javax.quantities.Length;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.INode;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
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
	
	public final void testEquals(){
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> length = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Dimensionless> roughCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultPipe pipe = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();

		DefaultPipe pipe2 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();
		
		DefaultPipe pipe3 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT))
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();
		
		DefaultPipe pipe4 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT)).build();
		
		DefaultPipe pipe5 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(null)
		.length(null)
		.lossCoefficient(null)
		.roughnessCoefficient(null).build();
		
		DefaultPipe pipe6 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(null)
		.length(null)
		.lossCoefficient(null)
		.roughnessCoefficient(null).build();
		
		DefaultPipe pipe7 = new DefaultPipe.Builder(null, netWorkMock)
		.diameter(null)
		.length(null)
		.lossCoefficient(null)
		.roughnessCoefficient(null).build();
		
		assertFalse(pipe.equals(new Object()));
		
		assertTrue(pipe.equals(pipe2));
		assertTrue(pipe2.equals(pipe));
		
		assertFalse(pipe.equals(pipe3));
		assertFalse(pipe.equals(pipe4));
		assertFalse(pipe.equals(pipe5));
		
		assertFalse(pipe5.equals(pipe));
		assertFalse(pipe6.equals(pipe7));
		assertFalse(pipe7.equals(pipe6));
		assertEquals(pipe5, pipe6);
		
		assertFalse(pipe.equals(null));
	}
	
	//in this case, if equals is true hashcodes are the same
	public final void testHashCode(){
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> length = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Dimensionless> roughCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultPipe pipe = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();

		DefaultPipe pipe2 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();
		
		DefaultPipe pipe3 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT))
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();
		
		DefaultPipe pipe4 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT)).build();
		
		DefaultPipe pipe5 = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(null)
		.length(null)
		.lossCoefficient(null)
		.roughnessCoefficient(null).build();
		
		assertEquals(pipe.hashCode(), pipe2.hashCode());
		assertEquals(pipe2.hashCode(), pipe.hashCode());
		
		assertFalse(pipe.hashCode() == pipe3.hashCode());
		assertFalse(pipe.hashCode() == pipe4.hashCode());
		assertFalse(pipe.hashCode() == pipe5.hashCode());
	}
	
	public final void testGetStartNode(){
		
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> length = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Dimensionless> roughCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultPipe pipe = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();

		INode montanteNode = EasyMock.createMock(INode.class);
		EasyMock.expect(netWorkMock.getAnterior(pipe)).andReturn(montanteNode).anyTimes();
		EasyMock.replay(netWorkMock);
		
		assertEquals(montanteNode, pipe.getStartNode());
	}
	
	public final void testGetEndNode(){
		
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> length = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Dimensionless> roughCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultPipe pipe = new DefaultPipe.Builder("defPipe", netWorkMock)
		.diameter(diameter)
		.length(length)
		.lossCoefficient(lossCoef)
		.roughnessCoefficient(roughCoef).build();

		INode jusanteNode = EasyMock.createMock(INode.class);
		EasyMock.expect(netWorkMock.getProximo(pipe)).andReturn(jusanteNode).anyTimes();
		EasyMock.replay(netWorkMock);
		
		assertEquals(jusanteNode, pipe.getEndNode());
	}
}
