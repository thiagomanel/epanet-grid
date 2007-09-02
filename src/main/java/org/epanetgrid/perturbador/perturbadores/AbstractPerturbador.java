/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores;

import org.epanetgrid.model.NetworkComponent;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
public abstract class AbstractPerturbador<T extends NetworkComponent> implements IPerturbador<T> {

	private final String componentLabel;
	private final double newValue;

	/**
	 * @param componentLabel
	 * @param newValue
	 */
	protected AbstractPerturbador(String componentLabel, double newValue) {
		this.componentLabel = componentLabel;
		this.newValue = newValue;
	}
	
	/**
	 * @return
	 */
	protected double getNewValue(){
		return this.newValue;
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.IPerturbador#disturb(T)
	 */
	public abstract T disturb(T component);

	/* (non-Javadoc)
	 * @see org.epanetgrid.perturbador.perturbadores.IPerturbador#getComponentLabel()
	 */
	public String getComponentLabel() {
		return this.componentLabel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((componentLabel == null) ? 0 : componentLabel.hashCode());
		long temp;
		temp = Double.doubleToLongBits(newValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractPerturbador other = (AbstractPerturbador) obj;
		if (componentLabel == null) {
			if (other.componentLabel != null)
				return false;
		} else if (!componentLabel.equals(other.componentLabel))
			return false;
		if (Double.doubleToLongBits(newValue) != Double
				.doubleToLongBits(other.newValue))
			return false;
		return true;
	}

}
