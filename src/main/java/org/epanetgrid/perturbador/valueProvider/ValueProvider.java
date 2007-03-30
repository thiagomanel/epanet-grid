/**
 * 
 */
package org.epanetgrid.perturbador.valueProvider;

/**
 * Interface que gera o novo valor da propriedade perturbada.
 * Implementaes distinstas desta interface para a simulao
 * Monte Carlo e para anlise de sensibilidade devem existir, por
 * exemplo a Monte Carlo usa uma semente randomica na gerao do valor
 * perturbado.  
 * @author thiagoepdc
 */
public interface ValueProvider {

	/**
	 * 
	 * @return
	 */
	public double[] getValorPerturbado();
}
