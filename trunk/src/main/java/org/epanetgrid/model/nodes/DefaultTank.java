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
	private final Measure<Length> minimumWaterLevel;
	private final Measure<Length> maximumSecurityLevel;
	private final Measure<Length> minimumSecurityLevel;
	private final Measure<Volume> minimumVolume;
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
	 * @see org.epanetgrid.model.nodes.ITank#getMinimumWaterLevel()
	 */
	public Measure<Length> getMinimumWaterLevel() {
		return this.minimumWaterLevel;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getMaximumSecurityLevel()
	 */
	public Measure<Length> getMaximumSecurityLevel() {
		return this.maximumSecurityLevel;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getMinimumSecurityLevel()
	 */
	public Measure<Length> getMinimumSecurityLevel() {
		return this.minimumSecurityLevel;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.ITank#getMinimuVolume()
	 */
	public Measure<Volume> getMinimumVolume() {
		return this.minimumVolume;
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
		this.maximumSecurityLevel = builder.maximumSecurityLevel;
		this.minimumSecurityLevel = builder.minimumSecurityLevel;
		this.minimumVolume = builder.minimumVolume;
		this.nominalDiameter = builder.nominalDiameter;
		this.volumeCurveID = builder.volumeCurveID;
	}

	public static class Builder{

		private String volumeCurveID;
		private Measure<Length> nominalDiameter;
		private Measure<Volume> minimumVolume;
		private Measure<Length> minimumWaterLevel;
		private Measure<Length> maximumWaterLevel;
		private Measure<Length> minimumSecurityLevel;
		private Measure<Length> maximumSecurityLevel;
		private String label;
		private Measure<Length> initialWaterLevel;
		private Measure<Length> elevation;
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
			minimumSecurityLevel(tank.getMinimumSecurityLevel());
			maximumSecurityLevel(tank.getMaximumSecurityLevel());
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

		public Builder minimumSecurityLevel(Measure<Length> minimumSecurityLevel) {
			this.minimumSecurityLevel = minimumSecurityLevel;
			return this;
		}

		public Builder maximumSecurityLevel(Measure<Length> maximumSecurityLevel) {
			this.maximumSecurityLevel = maximumSecurityLevel;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((elevation == null) ? 0 : elevation.hashCode());
		result = PRIME * result + ((initialWaterLevel == null) ? 0 : initialWaterLevel.hashCode());
		result = PRIME * result + ((label == null) ? 0 : label.hashCode());
		result = PRIME * result + ((maximumWaterLevel == null) ? 0 : maximumWaterLevel.hashCode());
		result = PRIME * result + ((minimumVolume == null) ? 0 : minimumVolume.hashCode());
		result = PRIME * result + ((minimumWaterLevel == null) ? 0 : minimumWaterLevel.hashCode());
		result = PRIME * result + ((minimumSecurityLevel == null) ? 0 : minimumSecurityLevel.hashCode());
		result = PRIME * result + ((maximumSecurityLevel == null) ? 0 : maximumSecurityLevel.hashCode());
		result = PRIME * result + ((nominalDiameter == null) ? 0 : nominalDiameter.hashCode());
		result = PRIME * result + ((volumeCurveID == null) ? 0 : volumeCurveID.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final DefaultTank other = (DefaultTank) obj;
		
		if (elevation == null) {
			if (other.elevation != null) {
				return false;
			}
		} else if (!elevation.equals(other.elevation)) {
			return false;
		}

		if (initialWaterLevel == null) {
			if (other.initialWaterLevel != null) {
				return false;
			}
		} else if (!initialWaterLevel.equals(other.initialWaterLevel)) {
			return false;
		}
		
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		
		if (maximumWaterLevel == null) {
			if (other.maximumWaterLevel != null) {
				return false;
			}
		} else if (!maximumWaterLevel.equals(other.maximumWaterLevel)) {
			return false;
		}
		
		if (minimumVolume == null) {
			if (other.minimumVolume != null) {
				return false;
			}
		} else if (!minimumVolume.equals(other.minimumVolume)) {
			return false;
		}
	
		if (minimumWaterLevel == null) {
			if (other.minimumWaterLevel != null) {
				return false;
			}
		} else if (!minimumWaterLevel.equals(other.minimumWaterLevel)) {
			return false;
		}
		
		if (minimumSecurityLevel == null) {
			if (other.minimumSecurityLevel != null) {
				return false;
			}
		} else if (!minimumSecurityLevel.equals(other.minimumSecurityLevel)) {
			return false;
		}
		
		if (maximumSecurityLevel == null) {
			if (other.maximumSecurityLevel != null) {
				return false;
			}
		} else if (!maximumSecurityLevel.equals(other.maximumSecurityLevel)) {
			return false;
		}
		
		if (nominalDiameter == null) {
			if (other.nominalDiameter != null) {
				return false;
			}
		} else if (!nominalDiameter.equals(other.nominalDiameter)) {
			return false;
		}
		
		if (volumeCurveID == null) {
			if (other.volumeCurveID != null) {
				return false;
			}
		} else if (!volumeCurveID.equals(other.volumeCurveID)) {
			return false;
		}
		
		return true;
	}

}
