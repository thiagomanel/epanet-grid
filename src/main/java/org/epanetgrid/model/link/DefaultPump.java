/**
 * 
 */
package org.epanetgrid.model.link;

import javax.quantities.Dimensionless;
import javax.quantities.Energy;

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
public class DefaultPump implements IPump<DefaultPump> {

	private final String headCurveID;
	private final Measure<Energy> power;
	private final Measure<Dimensionless> relativeSped;
	private final String idTimePattern;
	private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;
	private final String label;

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPump#getHeadCurveID()
	 */
	public String getHeadCurveID() {
		return this.headCurveID;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPump#getPower()
	 */
	public Measure<Energy> getPower() {
		return this.power;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPump#getRelativeSpeed()
	 */
	public Measure<Dimensionless> getRelativeSpeed() {
		return this.relativeSped;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPump#idTimePattern()
	 */
	public String idTimePattern() {
		return this.idTimePattern;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.NetworkComponent#label()
	 */
	public String label() {
		return this.label;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.ILink#getEndNode()
	 */
	public INode<?> getEndNode() {
		return this.network.getProximo(this);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.ILink#getStarNode()
	 */
	public INode<?> getStartNode() {
		return this.network.getAnterior(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if(obj instanceof DefaultPump){
			DefaultPump other = (DefaultPump) obj;
			if (this.headCurveID == null) {
				if(other.headCurveID != null){
					return false;
				}
			} else if(!this.headCurveID.equals(other.headCurveID)) {
				return false;
			}
			if (this.power == null) {
				if(other.power != null){
					return false;
				}
			} else if(!this.power.equals(other.power)) {
				return false;
			}
			if (this.relativeSped == null) {
				if(other.relativeSped != null){
					return false;
				}
			} else if(!this.relativeSped.equals(other.relativeSped)) {
				return false;
			}
			if (this.idTimePattern == null) {
				if(other.idTimePattern != null){
					return false;
				}
			} else if(!this.idTimePattern.equals(other.idTimePattern)) {
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
	
	/**
	 * 
	 */
	private DefaultPump(Builder builder) {
		this.headCurveID = builder.headCurveID;
		this.idTimePattern = builder.idTimePattern;
		this.label = builder.label;
		this.power = builder.power;
		this.relativeSped = builder.relativeSped;
		this.network = builder.network;
	}

	public static class Builder {
		
		private String headCurveID;
		private Measure<Energy> power;
		private Measure<Dimensionless> relativeSped;
		private String idTimePattern;
		private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;
		private String label;
		
		/**
		 * @param label
		 * @param netWork
		 */
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(IPump<?> pump){
			headCurveID(pump.getHeadCurveID());
			power(pump.getPower());
			relativeSped(pump.getRelativeSpeed());
			idTimePattern(pump.idTimePattern());
			return this;
		}

		public Builder headCurveID(String curveID) {
			this.headCurveID = curveID;
			return this;
		}

		public Builder power(Measure<Energy> power) {
			this.power = power;
			return this;
		}

		public Builder relativeSped(Measure<Dimensionless> relativeSpeed) {
			this.relativeSped = relativeSpeed;
			return this;
		}

		public Builder idTimePattern(String idTimePattern) {
			this.idTimePattern = idTimePattern; 
			return this;
		}
		
		public DefaultPump build(){
			return new DefaultPump(this);
		}
	}
}
