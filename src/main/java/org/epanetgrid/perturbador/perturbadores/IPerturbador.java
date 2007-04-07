/**
 * 
 */
package org.epanetgrid.perturbador.perturbadores;

import org.epanetgrid.model.NetworkComponent;

/**
 * @author thiagoepdc
 *
 */
public interface IPerturbador<T extends NetworkComponent> {

	/**
	 * @param component
	 * @return
	 */
	public T disturb(T component);
	
	/**
	 * @return
	 */
	public String getComponentLabel();
}
