/**
 * 
 */
package org.epanetgrid.perturbador.valueProvider;


/**
 * @author thiagoepdc
 */
public class MonteCarloValueProvider implements ValueProvider { 

	private double extremoInferior, extremoSuperior;
	private double faixaVariacao;
	private int numSementes;
	private double valorMedio;
	private RandomSeed seed;
	
	/**
	 * 
	 * @param extremoInferior
	 * @param extremoSuperior
	 * @param numSementes
	 * @param seed
	 */
	public MonteCarloValueProvider(double extremoInferior, double extremoSuperior, int numSementes, RandomSeed seed){
	
		if(extremoInferior > extremoSuperior) {
			throw new IllegalArgumentException("O extremo inferior no pode ser maior que " +
					"o extremo superior.");
		}
		
		this.seed = seed;
		this.extremoInferior = extremoInferior;
		this.extremoSuperior = extremoSuperior;
		this.numSementes = numSementes;
		this.faixaVariacao = Double.NaN;
		this.valorMedio = Double.NaN;
	}
	
	/**
	 * 
	 * @param numSementes
	 * @param faixaVariacao
	 * @param valorMedio
	 * @param seed
	 */
	public MonteCarloValueProvider(int numSementes, double faixaVariacao, double valorMedio, RandomSeed seed){

		if(faixaVariacao < 0 ){
			throw new IllegalArgumentException("O valor da faixa de variao deve ser positiva.");
		}
		
		this.seed = seed;
		this.faixaVariacao = faixaVariacao;
		this.numSementes = numSementes;
		this.valorMedio = valorMedio;
		calculaExtremos();
	}
	
	/* (non-Javadoc)
	 * @see org.smartpumping.core.avaliadorRobustez.perturbador.ValueProvider#getValorPerturbado()
	 */
	public double[] getValorPerturbado() {
		
		double[] seeds = new double[numSementes];
		for (int i = 0; i < seeds.length; i++) {
			seeds[i] = extremoInferior + ( (seed.getSeed()) * (extremoSuperior - extremoInferior) );
		}
		return seeds;
	}
	
	/**
	 * Calcula os extremos caso, use a opo com faixa de variao e valorMedio. 
	 */
	private void calculaExtremos(){
		if(valorMedio != Double.NaN){//usando a opcao com faixa de variao
			extremoInferior = (valorMedio) * (1 - (faixaVariacao));
			extremoSuperior = (valorMedio) * (1 + (faixaVariacao));
		}
	}

}
