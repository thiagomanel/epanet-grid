package org.epanetgrid.model.nodes;

import java.util.Random;

import javax.quantities.Length;
import javax.quantities.Volume;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

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
	
	public final void testEquals(){
		
		Measure<Length> initialWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> maximumWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> minimumWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Volume> minimumVolume = Measure.valueOf(new Random().nextDouble(), Volume.SI_UNIT);
		Measure<Length> nominalDiameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		
		String volumeCurveID = "volumeCurveID";
		
		NetWork networkMock = EasyMock.createMock(NetWork.class);
		
		DefaultTank tank = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(maximumWaterLevel)
		.minimumVolume(minimumVolume)
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank2 = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(maximumWaterLevel)
		.minimumVolume(minimumVolume)
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank3 = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT))
		.minimumVolume(minimumVolume)
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank4 = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(maximumWaterLevel)
		.minimumVolume(Measure.valueOf(new Random().nextDouble(), Volume.SI_UNIT))
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank5 = new DefaultTank.Builder(null, networkMock)
		.initialWaterLevel(null)
		.maximumWaterLevel(null)
		.elevation(null)
		.minimumVolume(null)
		.minimumWaterLevel(null)
		.nominalDiameter(null)
		.volumeCurveID(null)
		.build();
		
		DefaultTank tank6 = new DefaultTank.Builder(null, networkMock)
		.initialWaterLevel(null)
		.elevation(null)
		.maximumWaterLevel(null)
		.minimumVolume(null)
		.minimumWaterLevel(null)
		.nominalDiameter(null)
		.volumeCurveID(null)
		.build();
		
		assertTrue(tank.equals(tank2));
		assertTrue(tank2.equals(tank));
		assertTrue(tank5.equals(tank6));

		assertFalse(tank.equals(tank3));
		assertFalse(tank.equals(tank4));
		assertFalse(tank.equals(tank5));
		assertFalse(tank5.equals(tank));
		assertFalse(tank6.equals(tank));
		
		assertFalse(tank.equals(null));
		
		assertFalse(tank.equals(new Object()));
	}
	
	public final void testHashCode(){
			
		Measure<Length> initialWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> maximumWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Length> minimumWaterLevel = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		Measure<Volume> minimumVolume = Measure.valueOf(new Random().nextDouble(), Volume.SI_UNIT);
		Measure<Length> nominalDiameter = Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT);
		
		String volumeCurveID = "volumeCurveID";
		
		NetWork networkMock = EasyMock.createMock(NetWork.class);
		
		DefaultTank tank = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(maximumWaterLevel)
		.minimumVolume(minimumVolume)
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank2 = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(maximumWaterLevel)
		.minimumVolume(minimumVolume)
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank3 = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(Measure.valueOf(new Random().nextDouble(), Length.SI_UNIT))
		.minimumVolume(minimumVolume)
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank4 = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(initialWaterLevel)
		.maximumWaterLevel(maximumWaterLevel)
		.minimumVolume(Measure.valueOf(new Random().nextDouble(), Volume.SI_UNIT))
		.minimumWaterLevel(minimumWaterLevel)
		.nominalDiameter(nominalDiameter)
		.volumeCurveID(volumeCurveID)
		.build();
		
		DefaultTank tank5 = new DefaultTank.Builder("DefaultTank", networkMock)
		.initialWaterLevel(null)
		.maximumWaterLevel(null)
		.minimumVolume(null)
		.minimumWaterLevel(null)
		.nominalDiameter(null)
		.volumeCurveID(null)
		.build();
		
		DefaultTank tank6 = new DefaultTank.Builder(null, networkMock)
		.initialWaterLevel(null)
		.elevation(null)
		.maximumWaterLevel(null)
		.minimumVolume(null)
		.minimumWaterLevel(null)
		.nominalDiameter(null)
		.volumeCurveID(null)
		.build();
		
		assertEquals(tank.hashCode(), tank2.hashCode());
		assertEquals(tank2, tank);
		
		assertFalse(tank.hashCode() == tank3.hashCode());
		assertFalse(tank.hashCode() == tank4.hashCode());
		assertFalse(tank.hashCode() == tank5.hashCode());
		assertFalse(tank.hashCode() == tank6.hashCode());
	}
}
