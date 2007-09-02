/**
 * 
 */
package org.epanetgrid.perturbador.valueProvider;

import java.util.ArrayList;
import java.util.List;


/**
 * @author thiagoepdc
 *
 */
public class AnaliseSensibilidadeValueProvider implements ValueProvider {

	private final double extremoInferior, extremoSuperior;
	private final double faixaVariacao;
	private final double valorMedio;
	private final Double discretizacao;
	
	 /** 
	 * @param extremoInferior
	 * @param extremoSuperior
	 * @param discretizacao
	 */
	protected AnaliseSensibilidadeValueProvider(double extremoInferior, double extremoSuperior, Double discretizacao){
		if(extremoInferior > extremoSuperior) {
			throw new IllegalArgumentException("O extremo inferior < "+ extremoInferior+" > no pode ser maior que " +
					"o extremo superior < "+extremoSuperior+" >");
		}
		
		if(new Double(discretizacao).doubleValue() <= 0){
			throw new IllegalArgumentException("O valor da discretizao deve ser positivo.");
		}
		
		this.extremoInferior = extremoInferior;
		this.extremoSuperior = extremoSuperior;
		this.discretizacao = discretizacao;
		this.faixaVariacao = Double.NaN;
		this.valorMedio = Double.NaN;
	}
	
	/**
	 * 
	 * @param discretizacao
	 * @param faixaVariacao
	 * @param valorMedio
	 */
	protected AnaliseSensibilidadeValueProvider(Double discretizacao, double faixaVariacao, double valorMedio){

		if(faixaVariacao < 0 ){
			throw new IllegalArgumentException("O valor da faixa de variao deve ser positiva.");
		}
		
		if(new Double(discretizacao).doubleValue() <= 0){
			throw new IllegalArgumentException("O valor da discretizao deve ser positivo.");
		}
		
		this.discretizacao = discretizacao;
		this.faixaVariacao = faixaVariacao;
		this.valorMedio = valorMedio;
		this.extremoInferior = calculaExtremoInferior(valorMedio, faixaVariacao);
		this.extremoSuperior = calculaExtremoInferior(valorMedio, faixaVariacao);
	}
	
	/* (non-Javadoc)
	 * @see org.smartpumping.core.avaliadorRobustez.perturbador.ValueProvider#getValorPerturbado()
	 */
	public double[] getValorPerturbado() {
		//TODO: OTIMIZAR 0 CODIGO;
		List<Double> seeds = new ArrayList<Double>();
		
		double tempValue = extremoInferior;
		
		while( (tempValue) < (extremoSuperior)){
			seeds.add(tempValue);
			tempValue = tempValue + (discretizacao * (extremoInferior));
		}
		
		seeds.add(extremoSuperior);
		double[] seedReturn = new double[seeds.size()];
		for (Double double1 : seeds) {
			seedReturn[seeds.indexOf(double1)] = double1.doubleValue();
		}
		return seedReturn;
	}
	
	/**
	 * Calcula os extremos caso, use a opo com faixa de variao e valorMedio. 
	 */
	private double calculaExtremoSuperior(double valorMedio, double faixaVariacao){
		
		if(valorMedio != Double.NaN){//usando a opcao com faixa de variao
			return (valorMedio) * (1 + ((valorMedio >= 0) ? faixaVariacao : - faixaVariacao));
		}
		throw new IllegalArgumentException("Was not possible assign the extreme value, the" +
				"average value is a NaN value");
	}

	/**
	 * Calcula os extremos caso, use a opo com faixa de variao e valorMedio. 
	 */
	private double calculaExtremoInferior(double valorMedio, double faixaVariacao){
		
		if(valorMedio != Double.NaN){//usando a opcao com faixa de variao
			return (valorMedio) * (1 - ((valorMedio >= 0) ? faixaVariacao : - faixaVariacao));
		}				
		
		throw new IllegalArgumentException("Was not possible assign the extreme value, the" +
			"average value is a NaN value");
	}
}
