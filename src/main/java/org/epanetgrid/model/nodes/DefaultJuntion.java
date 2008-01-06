/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;
import javax.quantities.Pressure;
import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
public class DefaultJuntion implements IJunction<DefaultJuntion> {

	private final Measure<Length> elevation;
	private final Measure<Pressure> maxPressure;
	private final Measure<Pressure> minPressure;
	private final Measure<VolumetricFlowRate> baseDemandFlow;
	private final String demandPatternID;
	private final String label;

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.IJunction#getElevation()
	 */
	public Measure<Length> getElevation() {
		return this.elevation;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.IJunction#getMaxPressure()
	 */
	public Measure<Pressure> getMaxPressure() {
		return this.maxPressure;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.IJunction#getMinPressure()
	 */
	public Measure<Pressure> getMinPressure() {
		return this.minPressure;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.IJunction#getBaseDemandFlow()
	 */
	public Measure<VolumetricFlowRate> getBaseDemandFlow() {
		return this.baseDemandFlow;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.IJunction#getDemandPatternID()
	 */
	public String getDemandPatternID() {
		return this.demandPatternID;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.NetworkComponent#label()
	 */
	public String label() {
		return this.label;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(obj instanceof DefaultJuntion){
			DefaultJuntion other = (DefaultJuntion) obj;
			if (this.elevation == null) {
				if(other.elevation != null){
					return false;
				}
			} else if(!this.elevation.equals(other.elevation)) {
				return false;
			}
			if (this.maxPressure == null) {
				if(other.maxPressure != null){
					return false;
				}
			} else if(!this.maxPressure.equals(other.maxPressure)) {
				return false;
			}
			if (this.minPressure == null) {
				if(other.minPressure != null){
					return false;
				}
			} else if(!this.minPressure.equals(other.minPressure)) {
				return false;
			}
			if (this.baseDemandFlow == null) {
				if(other.baseDemandFlow != null){
					return false;
				}
			} else if(!this.baseDemandFlow.equals(other.baseDemandFlow)) {
				return false;
			}
			if (this.demandPatternID == null) {
				if(other.demandPatternID != null){
					return false;
				}
			} else if(!this.demandPatternID.equals(other.demandPatternID)) {
				return false;
			}
			if (this.label == null) {
				if(other.label() != null){
					return false;
				}
			} else if(!this.label.equals(other.label())) {
				return false;
			}
			
			return true;
		}
		return false;
	}

//	avoiding extern call
	private DefaultJuntion(Builder builder){
		this.elevation = builder.elevation;
		this.maxPressure = builder.maxPressure;
		this.minPressure = builder.minPressure;
		this.baseDemandFlow = builder.baseDemandFlow;
		this.demandPatternID = builder.demandPatternID;
		this.label = builder.label;
	}
	
	public static class Builder {
		
		protected Measure<Length> elevation;
		protected Measure<Pressure> maxPressure;
		protected Measure<Pressure> minPressure;
		protected Measure<VolumetricFlowRate> baseDemandFlow;
		protected String demandPatternID;
		protected NetWork network;
		protected String label;
		
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(IJunction<?> junction){
			elevation(junction.getElevation());
			maxPressure(junction.getMaxPressure());
			minPressure(junction.getMinPressure());
			baseDemandFlow(junction.getBaseDemandFlow());
			demandPatternID(junction.getDemandPatternID());
			return this;
		}

		public Builder elevation(Measure<Length> elevation) {
			this.elevation = elevation;
			return this;
		}
		
		public Builder maxPressure(Measure<Pressure> maxPressure) {
			this.maxPressure = maxPressure;
			return this;
		}

		public Builder minPressure(Measure<Pressure> minPressure) {
			this.minPressure = minPressure;
			return this;
		}

		public Builder baseDemandFlow(Measure<VolumetricFlowRate> baseDemandFlow) {
			this.baseDemandFlow = baseDemandFlow;
			return this;
		}

		public Builder demandPatternID(String demandPatternID) {
			this.demandPatternID = demandPatternID;
			return this;
		}
		
		public DefaultJuntion build(){
			return new DefaultJuntion(this);
		}
	}

}
