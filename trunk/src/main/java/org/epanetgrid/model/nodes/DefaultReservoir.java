/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author thiagoepdc
 *
 */
public class DefaultReservoir implements IReservoir<DefaultReservoir> {

	private final Measure<Length> head;
	private final String headPatternID;
	private final String label;
	private NetWork network;

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.IReservoir#getHead()
	 */
	public Measure<Length> getHead() {
		return this.head;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.nodes.IReservoir#getHeadPatternID()
	 */
	public String getHeadPatternID() {
		return this.headPatternID;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.NetworkComponent#label()
	 */
	public String label() {
		return this.label;
	}
	
	private DefaultReservoir(Builder builder){
		this.head = builder.head;
		this.headPatternID = builder.headPatternID;
		this.label = builder.label;
		this.network = builder.network;
	}
	
	public static class Builder {
		
		private Measure<Length> head;
		private String headPatternID;
		private String label;
		private NetWork network;
		
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(IReservoir<?> reservoir){
			head(reservoir.getHead());
			headPatternID(reservoir.getHeadPatternID());
			return this;
		}

		public Builder head(Measure<Length> head) {
			this.head = head;
			return this;
		}

		public Builder headPatternID(String headPatternID) {
			this.headPatternID = headPatternID;
			return this;
		}
		
		public DefaultReservoir build(){
			return new DefaultReservoir(this);
		}
	}
}
