package org.epanetgrid.model.nodes;

import java.util.Random;

import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class DefaultJuntionTest extends TestCase {

	public void testBuild(){
		
		Measure<Length> elevation = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		String demandPatternID = "demandPatternID";
		Measure<VolumetricFlowRate> baseDemandFlow = Measure.valueOf(new Random().nextDouble(), VolumetricFlowRate.SI_UNIT);
		
		DefaultJuntion junction = new DefaultJuntion.Builder("DefaultJuntion", EasyMock.createMock(NetWork.class))
		.baseDemandFlow(baseDemandFlow)
		.elevation(elevation)
		.demandPatternID(demandPatternID)
		.build();
		
		assertEquals(baseDemandFlow, junction.getBaseDemandFlow());
		assertEquals(elevation, junction.getElevation());
		assertEquals(demandPatternID, junction.getDemandPatternID());
	}
	
}
