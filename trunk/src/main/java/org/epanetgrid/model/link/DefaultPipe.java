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
public class DefaultPipe implements IPipe<DefaultPipe> {

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
		return null;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.model.ILink#getStarNode()
	 */
	public INode getStartNode() {
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
		
		protected Measure<Length> length;
		protected Measure<Length> diameter;
		protected Measure<Dimensionless> roughnessCoefficient;
		protected Measure<Dimensionless> lossCoefficient;
		protected NetWork network;
		protected String label;
		
		/**
		 * @param label
		 * @param netWork
		 */
		public Builder(String label, NetWork netWork){
			this.network = netWork;
			this.label = label;
		}
		
		public Builder copy(IPipe<?> pipe){
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		
		result = PRIME * result + ((diameter == null) ? 0 : diameter.hashCode());
		result = PRIME * result + ((label == null) ? 0 : label.hashCode());
		result = PRIME * result + ((length == null) ? 0 : length.hashCode());
		result = PRIME * result + ((lossCoefficient == null) ? 0 : lossCoefficient.hashCode());
		result = PRIME * result + ((network == null) ? 0 : network.hashCode());
		result = PRIME * result + ((roughnessCoefficient == null) ? 0 : roughnessCoefficient.hashCode());
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
		
		final DefaultPipe other = (DefaultPipe) obj;

		if (diameter == null) {
			if (other.diameter != null) {
				return false;
			}
		} else if (!diameter.equals(other.diameter)) {
			return false;
		}
		
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		
		if (length == null) {
			if (other.length != null) {
				return false;
			}
		} else if (!length.equals(other.length)) {
			return false;
		}
		
		if (lossCoefficient == null) {
			if (other.lossCoefficient != null) {
				return false;
			}
		} else if (!lossCoefficient.equals(other.lossCoefficient)) {
			return false;
		}
		
		if (network == null) {
			if (other.network != null) {
				return false;
			}
		} else if (!network.equals(other.network)) {
			return false;
		}
		if (roughnessCoefficient == null) {
			if (other.roughnessCoefficient != null) {
				return false;
			}
		} else if (!roughnessCoefficient.equals(other.roughnessCoefficient)) {
			return false;
		}
		
		return true;
	}
	
}
