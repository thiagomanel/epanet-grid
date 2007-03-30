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

	public T disturb(T component);
	
	public String getComponentLabel();
}
