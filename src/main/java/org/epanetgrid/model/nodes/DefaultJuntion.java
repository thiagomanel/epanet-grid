/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class DefaultJuntion implements IJunction<DefaultJuntion> {

	private final Measure<Length> elevation;
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
	
//	avoiding extern call
	private DefaultJuntion(Builder builder){
		this.elevation = builder.elevation;
		this.baseDemandFlow = builder.baseDemandFlow;
		this.demandPatternID = builder.demandPatternID;
		this.label = builder.label;
	}
	
	public static class Builder {
		
		public Measure<Length> elevation = Measure.valueOf(1, Length.SI_UNIT);
		public Measure<VolumetricFlowRate> baseDemandFlow = Measure.valueOf(1, VolumetricFlowRate.SI_UNIT);;
		public String demandPatternID;
		protected NetWork network;
		protected String label;
		
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(IJunction<?> junction){
			elevation(junction.getElevation());
			baseDemandFlow(junction.getBaseDemandFlow());
			demandPatternID(junction.getDemandPatternID());
			return this;
		}

		public Builder elevation(Measure<Length> elevation) {
			this.elevation = elevation;
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
