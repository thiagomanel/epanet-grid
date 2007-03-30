/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores;

import org.epanetgrid.model.NetworkComponent;

/**
 * @author thiagoepdc
 *
 */
public abstract class AbstractPerturbador<T extends NetworkComponent> implements IPerturbador<T> {

	private final String componentLabel;
	private final double newValue;

	protected AbstractPerturbador(String componentLabel, double newValue) {
		this.componentLabel = componentLabel;
		this.newValue = newValue;
	}
	
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
