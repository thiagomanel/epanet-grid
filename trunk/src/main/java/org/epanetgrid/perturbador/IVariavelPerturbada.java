/**
 * 
 */
package org.epanetgrid.perturbador;

import org.epanetgrid.perturbador.valueProvider.ValueProvider;

/**
 * @author thiagoepdc
 *
 */
public interface IVariavelPerturbada {

	/**
	 * @return
	 */
	public String getComponentLabel();
	
	/**
	 * @return
	 */
	public int getNumSamples();
	
	/**
	 * @return
	 */
	public ValueProvider getValueProvider();
}
