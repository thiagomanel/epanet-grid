package org.epanetgrid.model.link;

import java.util.Random;

import javax.quantities.Dimensionless;
import javax.quantities.Energy;
import javax.quantities.Length;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.INode;
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
	
	public final void testStartEndNode(){
		
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultValve valve = new DefaultValve.Builder("defValve", netWorkMock)
		.lossCoefficient(lossCoef)
		.diameter(diameter)
		.build();
		
		INode montanteNode = EasyMock.createMock(INode.class);
		EasyMock.expect(netWorkMock.getAnterior(valve)).andReturn(montanteNode).anyTimes();
		EasyMock.replay(netWorkMock);
		
		assertEquals(montanteNode, valve.getStartNode());
	}
	
	public final void testGetEndNode(){
		
		Measure<Length> diameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Dimensionless> lossCoef = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultValve valve = new DefaultValve.Builder("defValve", netWorkMock)
		.lossCoefficient(lossCoef)
		.diameter(diameter)
		.build();
		
		INode jusanteNode = EasyMock.createMock(INode.class);
		EasyMock.expect(netWorkMock.getProximo(valve)).andReturn(jusanteNode).anyTimes();
		EasyMock.replay(netWorkMock);
		
		assertEquals(jusanteNode, valve.getEndNode());
	}
}
