/**
 * 
 */
package org.epanetgrid.model.link;

import javax.quantities.Dimensionless;
import javax.quantities.Length;

import org.epanetgrid.model.INode;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public class DefaultPipe implements IPipe {

	private final Measure<Length> length;
	private final Measure<Length> diameter;
	private final Measure<Dimensionless> roughnessCoefficient;
	private final Measure<Dimensionless> lossCoefficient;
	private final NetWork network;
	private final String label;

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPipe#getLength()
	 */
	public Measure<Length> getLength() {
		return this.length;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPipe#getDiameter()
	 */
	public Measure<Length> getDiameter() {
		return this.diameter;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPipe#getRoughnessCoefficient()
	 */
	public Measure<Dimensionless> getRoughnessCoefficient() {
		return this.roughnessCoefficient;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPipe#getLossCoefficient()
	 */
	public Measure<Dimensionless> getLossCoefficient() {
		return this.lossCoefficient;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.link.IPipe#getStatus()
	 */
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.ILink#getStarNode()
	 */
	public INode getStarNode() {
		return this.network.getAnterior(this);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.ILink#getEndNode()
	 */
	public INode getEndNode() {
		return this.network.getProximo(this);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.NetworkComponent#label()
	 */
	public String label() {
		return this.label;
	}
	
	//avoiding extern call
	private DefaultPipe(Builder builder){
		this.diameter = builder.diameter;
		this.roughnessCoefficient = builder.roughnessCoefficient;
		this.lossCoefficient = builder.lossCoefficient;
		this.network = builder.network;
		this.label = builder.label;
		this.length = builder.length;
	}
	
	public static class Builder {
		
		protected Measure<Length> length = Measure.valueOf(1, Length.SI_UNIT);
		protected Measure<Length> diameter = Measure.valueOf(1, Length.SI_UNIT);;
		protected Measure<Dimensionless> roughnessCoefficient = Measure.valueOf(1, Dimensionless.SI_UNIT);
		protected Measure<Dimensionless> lossCoefficient = Measure.valueOf(1, Dimensionless.SI_UNIT);
		protected NetWork network;
		protected String label;
		
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(IPipe pipe){
			diameter(pipe.getDiameter());
			length(pipe.getLength());
			lossCoefficient(pipe.getLossCoefficient());
			roughnessCoefficient(pipe.getRoughnessCoefficient());
			return this;
		}

		public Builder diameter(Measure<Length> diameter) {
			this.diameter = diameter;
			return this;
		}

		public Builder length(Measure<Length> length) {
			this.length = length;
			return this;
		}

		public Builder lossCoefficient(Measure<Dimensionless> lossCoefficient) {
			this.lossCoefficient = lossCoefficient;
			return this;
		}

		public Builder roughnessCoefficient(Measure<Dimensionless> roughnessCoefficient) {
			this.roughnessCoefficient = roughnessCoefficient;
			return this;
		}
		
		public DefaultPipe build(){
			return new DefaultPipe(this);
		}
	}
}
