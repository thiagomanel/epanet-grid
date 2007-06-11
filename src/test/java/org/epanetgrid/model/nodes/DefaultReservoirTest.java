package org.epanetgrid.model.nodes;

import java.util.Random;

import javax.quantities.Length;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class DefaultReservoirTest extends TestCase {

	public void testBuild(){
		
		Measure<Length> head = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		String headPatternID = "headPatternID";
		
		DefaultReservoir reservoir = new DefaultReservoir.Builder("DefaultReservoir", EasyMock.createMock(NetWork.class))
		.head(head)
		.headPatternID(headPatternID)
		.build();
		
		assertEquals(head, reservoir.getHead());
		assertEquals(headPatternID, reservoir.getHeadPatternID());
	}
	
}
