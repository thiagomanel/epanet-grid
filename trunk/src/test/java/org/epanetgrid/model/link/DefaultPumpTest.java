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
public class DefaultPumpTest extends TestCase {

	public void testBuild(){
		
		Measure<Dimensionless> relativeSpeed = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Energy> power = Measure.valueOf(new Random().nextDouble(), Energy.SI_UNIT);
		String fooHeadCurve = "fooHeadCurve";
		String baaIDTimePatter = "baaIDTimePatter";
		
		DefaultPump pump = new DefaultPump.Builder("defPump", EasyMock.createMock(NetWork.class))
		.relativeSped(relativeSpeed)
		.power(power)
		.headCurveID(fooHeadCurve)
		.idTimePattern(baaIDTimePatter).build();
		
		assertEquals(relativeSpeed, pump.getRelativeSpeed());
		assertEquals(power, pump.getPower());
		assertEquals(fooHeadCurve, pump.getHeadCurveID());
		assertEquals(baaIDTimePatter, pump.idTimePattern());
	}
	
	public final void testGetStartNode(){
		
		Measure<Dimensionless> relativeSpeed = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Energy> power = Measure.valueOf(new Random().nextDouble(), Energy.SI_UNIT);
		String fooHeadCurve = "fooHeadCurve";
		String baaIDTimePatter = "baaIDTimePatter";
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultPump pump = new DefaultPump.Builder("defPump", netWorkMock)
		.relativeSped(relativeSpeed)
		.power(power)
		.headCurveID(fooHeadCurve)
		.idTimePattern(baaIDTimePatter).build();

		INode montanteNode = EasyMock.createMock(INode.class);
		EasyMock.expect(netWorkMock.getAnterior(pump)).andReturn(montanteNode).anyTimes();
		EasyMock.replay(netWorkMock);
		
		assertEquals(montanteNode, pump.getStartNode());
	}
	
	public final void testGetEndNode(){
		
		Measure<Dimensionless> relativeSpeed = Measure.valueOf(new Random().nextDouble(), Dimensionless.SI_UNIT);
		Measure<Energy> power = Measure.valueOf(new Random().nextDouble(), Energy.SI_UNIT);
		String fooHeadCurve = "fooHeadCurve";
		String baaIDTimePatter = "baaIDTimePatter";
		
		NetWork netWorkMock = EasyMock.createMock(NetWork.class);
		
		DefaultPump pump = new DefaultPump.Builder("defPump", netWorkMock)
		.relativeSped(relativeSpeed)
		.power(power)
		.headCurveID(fooHeadCurve)
		.idTimePattern(baaIDTimePatter).build();

		INode jusanteNode = EasyMock.createMock(INode.class);
		EasyMock.expect(netWorkMock.getProximo(pump)).andReturn(jusanteNode).anyTimes();
		EasyMock.replay(netWorkMock);
		
		assertEquals(jusanteNode, pump.getEndNode());
	}
}
