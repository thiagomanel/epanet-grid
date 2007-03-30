/**
 * 
 */
package org.epanetgrid.perturbador.valueProvider;


/**
 * Interface criada para prover gerao de nmero aleatrios entre
 * 0 e 1. Isto poderia ser feito sem a abstrao de um objeto, usando
 * a biblioteca padro matemtica da jdk. Mas esta soluo foi preterida
 * em favor de facilitar o uso dos testes de aceitao e Mocks objets.
 * @author thiagoepdc
 */
public interface RandomSeed {

	/**
	 * @return Uma semente randomica entre 0 e 1 inclusive.
	 */
	public double getSeed();
}
