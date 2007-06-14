/**
 * 
 */
package org.epanetgrid.perturbador;

import org.epanetgrid.perturbador.valueProvider.ValueProvider;

/**
 * Reune informações sobre as perturbacoes que podem acontecer
 * a um componente da rede: nome do componente, número de perturbacoes que
 * serao geradas e o gerador de valores para a perturbacao.
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
interface IVariavelPerturbada {

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
