/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;
import javax.quantities.Volume;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author thiagoepdc
 *
 */
public class DefaultTank implements ITank<DefaultTank> {

	private final Measure<Length> elevation;
	private final Measure<Length> initialWaterLevel;
	private final Measure<Length> maximumWaterLevel;
	private final Measure<Volume> minimumVolume;
	private final Measure<Length> minimumWaterLevel;
	private final Measure<Length> nominalDiameter;
	private final String volumeCurveID;
	private final String label;

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getElevation()
	 */
	public Measure<Length> getElevation() {
		return this.elevation;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getInitialWaterLevel()
	 */
	public Measure<Length> getInitialWaterLevel() {
		return this.initialWaterLevel;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getMaximumWaterLevel()
	 */
	public Measure<Length> getMaximumWaterLevel() {
		return this.maximumWaterLevel;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getMinimuVolume()
	 */
	public Measure<Volume> getMinimumVolume() {
		return this.minimumVolume;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getMinimumWaterLevel()
	 */
	public Measure<Length> getMinimumWaterLevel() {
		return this.minimumWaterLevel;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getNominalDiameter()
	 */
	public Measure<Length> getNominalDiameter() {
		return this.nominalDiameter;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getVolumeCurveID()
	 */
	public String getVolumeCurveID() {
		return this.volumeCurveID;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.NetworkComponent#label()
	 */
	public String label() {
		return this.label;
	}
	
	private DefaultTank(Builder builder){
		this.elevation = builder.elevation;
		this.initialWaterLevel = builder.initialWaterLevel;
		this.label = builder.label;
		this.maximumWaterLevel = builder.maximumWaterLevel;
		this.minimumWaterLevel = builder.minimumWaterLevel;
		this.minimumVolume = builder.minimumVolume;
		this.nominalDiameter = builder.nominalDiameter;
		this.volumeCurveID = builder.volumeCurveID;
	}

	public static class Builder{

		private String volumeCurveID;
		private Measure<Length> nominalDiameter = Measure.valueOf(1, Length.SI_UNIT);
		private Measure<Volume> minimumVolume = Measure.valueOf(1, Volume.SI_UNIT);
		private Measure<Length> minimumWaterLevel = Measure.valueOf(1, Length.SI_UNIT);
		private Measure<Length> maximumWaterLevel = Measure.valueOf(1, Length.SI_UNIT);
		private String label;
		private Measure<Length> initialWaterLevel = Measure.valueOf(1, Length.SI_UNIT);;
		private Measure<Length> elevation = Measure.valueOf(1, Length.SI_UNIT);
		private NetWork network;
		
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(ITank<?> tank){
			volumeCurveID(tank.getVolumeCurveID());
			nominalDiameter(tank.getNominalDiameter());
			minimumVolume(tank.getMinimumVolume());
			minimumWaterLevel(tank.getMinimumWaterLevel());
			maximumWaterLevel(tank.getMaximumWaterLevel());
			initialWaterLevel(tank.getInitialWaterLevel());
			elevation(tank.getElevation());
			return this;
		}

		public Builder volumeCurveID(String volumeCurveID) {
			this.volumeCurveID = volumeCurveID;
			return this;
		}

		public Builder nominalDiameter(Measure<Length> nominalDiameter) {
			this.nominalDiameter = nominalDiameter;
			return this;
		}
		
		public Builder minimumVolume(Measure<Volume> minimumVolume) {
			this.minimumVolume = minimumVolume;
			return this;
		}
		
		public Builder minimumWaterLevel(Measure<Length> minimumWaterLevel) {
			this.minimumWaterLevel = minimumWaterLevel;
			return this;
		}
		
		public Builder maximumWaterLevel(Measure<Length> maximumWaterLevel) {
			this.maximumWaterLevel = maximumWaterLevel;
			return this;
		}
		
		public Builder initialWaterLevel(Measure<Length> initialWaterLevel) {
			this.initialWaterLevel = initialWaterLevel;
			return this;
		}
		
		public Builder elevation(Measure<Length> elevation) {
			this.elevation = elevation;
			return this;
		}
		
		public DefaultTank build(){
			return new DefaultTank(this);
		} 
	}
}
