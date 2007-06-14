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

}
