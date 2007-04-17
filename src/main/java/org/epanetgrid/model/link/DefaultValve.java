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
	public INode getStarNode() {
		return this.network.getAnterior(this);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.NetworkComponent#label()
	 */
	public String label() {
		return this.label;
	}

	private DefaultValve(Builder builder){
		this.diameter = builder.diameter;
		this.label = builder.label;
		this.lossCoef = builder.lossCoef;
		this.network = builder.network;
	}
	
	public static class Builder {
		
		private Measure<Length> diameter;
		private Measure<Dimensionless> lossCoef;
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

		public Builder lossCoefficient(Measure<Dimensionless> lossCoef) {
			this.lossCoef = lossCoef;
			return this;
		}

		public DefaultValve build(){
			return new DefaultValve(this);
		}
	}

}
