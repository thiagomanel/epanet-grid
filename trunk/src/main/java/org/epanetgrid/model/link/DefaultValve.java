/**
 * 
 */
package org.epanetgrid.model.link;

import javax.quantities.Dimensionless;
import javax.quantities.Length;

import org.epanetgrid.model.INode;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.jscience.physics.measures.Measure;

/**
 * @author thiagoepdc
 *
 */
public class DefaultValve implements IValve<DefaultValve> {

	private final Measure<Length> diameter;
	private final Measure<Dimensionless> lossCoef;
	private String type;
	private String setting;
	private final String label;
	private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IValve#getDiameter()
	 */
	public Measure<Length> getDiameter() {
		return this.diameter;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IValve#getLossCoefficient()
	 */
	public Measure<Dimensionless> getLossCoefficient() {
		return this.lossCoef;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.ILink#getEndNode()
	 */
	public INode getEndNode() {
		return this.network.getProximo(this);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.ILink#getStarNode()
	 */
	public INode getStartNode() {
		return this.network.getAnterior(this);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IValve#getSetting()
	 */
	public String getSetting() {
		return this.setting;
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IValve#getType()
	 */
	public String getType() {
		return this.type;
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

		if(obj instanceof DefaultValve){
			DefaultValve other = (DefaultValve) obj;
			if (this.diameter == null) {
				if(other.diameter != null){
					return false;
				}
			} else if(!this.diameter.equals(other.diameter)) {
				return false;
			}
			if (this.lossCoef == null) {
				if(other.lossCoef != null){
					return false;
				}
			} else if(!this.lossCoef.equals(other.lossCoef)) {
				return false;
			}
			if (this.setting == null) {
				if(other.setting != null){
					return false;
				}
			} else if(!this.setting.equals(other.setting)) {
				return false;
			}
			if (this.type == null) {
				if(other.type != null){
					return false;
				}
			} else if(!this.type.equals(other.type)) {
				return false;
			}
			if (this.label == null) {
				if(other.label() != null){
					return false;
				}
			} else if(!this.label.equals(other.label())) {
				return false;
			}
			if (getStartNode() == null) {
				if (other.getStartNode() != null) {
					return false;
				}
			} else if(!getStartNode().equals(other.getStartNode())){
				return false;
			}
			if (getEndNode() == null) {
				if (other.getEndNode() != null) {
					return false;
				}
			} else if(!getEndNode().equals(other.getEndNode())){
				return false;
			}
			return true;
		}
		return false;
	}
	
	private DefaultValve(Builder builder){
		this.diameter = builder.diameter;
		this.label = builder.label;
		this.lossCoef = builder.lossCoef;
		this.setting = builder.setting;
		this.type = builder.type;
		this.network = builder.network;
	}
	
	public static class Builder {
		
		private Measure<Length> diameter;
		private Measure<Dimensionless> lossCoef;
		private String type;
		private String setting;
		private String label;
		private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;
		
		/**
		 * @param label
		 * @param netWork
		 */
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(IValve<?> valve){
			diameter(valve.getDiameter());
			lossCoefficient(valve.getLossCoefficient());
			return this;
		}

		public Builder diameter(Measure<Length> diameter) {
			this.diameter = diameter;
			return this;
		}
		
		public Builder type(String type) {
			this.type = type;
			return this;
		}
		
		public Builder setting(String setting) {
			this.setting = setting;
			return this;
		}

		public Builder lossCoefficient(Measure<Dimensionless> lossCoef) {
			this.lossCoef = lossCoef;
			return this;
		}

		public DefaultValve build(){
			return new DefaultValve(this);
		}
	}

}
