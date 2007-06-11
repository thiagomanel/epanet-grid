package org.epanetgrid.model.nodes;

import java.util.Random;

import javax.quantities.Length;
import javax.quantities.Volume;
import javax.quantities.VolumetricFlowRate;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class DefaultTankTest extends TestCase {
	
	public void testBuild(){
		
		Measure<Length> initialWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> maximumWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> minimumWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Volume> minimumVolume = Measure.valueOf(new Random().nextDouble(), Volume.SI_UNIT);
		Measure<Length> nominalDiameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		
		String volumeCurveID = "volumeCurveID";
		
		DefaultTank tank = new DefaultTank.Builder("DefaultTank", EasyMock.createMock(NetWork.class))
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(maximumWaterLevel)
		.minimumVolume(minimumVolume)
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		assertEquals(initialWaterLevel, tank.getInitialWaterLevel());
		assertEquals(maximumWaterLevel, tank.getMaximumWaterLevel());
		assertEquals(minimumVolume, tank.getMinimumVolume());
		assertEquals(minimumWaterLevel, tank.getMinimumWaterLevel());
		assertEquals(nominalDiameter, tank.getNominalDiameter());
		assertEquals(volumeCurveID, tank.getVolumeCurveID());
		
	}
}
