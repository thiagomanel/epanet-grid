/**
 * 
 */
package org.epanetgrid.perturbador.valueProvider;

import java.util.Random;

/**
 * @author thiago
 *
 */
public class SimpleValueProvider {
	//isto era pra ser uma static factory

	private SimpleValueProvider(){
		//avoiding extern creation
	}

	/**
	 * Discretizacao feita em intervalos constantes
	 * @param minValue
	 * @param maxValue
	 * @param numSamples
	 * @return
	 */
	public static ValueProvider getValueProvider(double minValue, double maxValue, int numSamples){
		if(numSamples <= 0) throw new IllegalArgumentException("Numero de amostragens deve ser positivo.");
		double discretizacao = new Double(((maxValue - minValue)/ (numSamples-1) / minValue));
		return new AnaliseSensibilidadeValueProvider(minValue, maxValue, new Double(discretizacao));
	}
	
	/**
	 * Discretizacao feita em intervalos constantes
	 * @param percentualVar
	 * @param averageValue
	 * @param numSamples
	 * @return
	 */
	public static ValueProvider getValueProviderVariacaoPercentual(double percentualVar, double averageValue, int numSamples){
		if(numSamples <= 0) throw new IllegalArgumentException("Numero de amostragens deve ser positivo.");
		double maxValue = averageValue + (averageValue * percentualVar);
		double minValue = averageValue - (averageValue * percentualVar);
		double discr = (maxValue - minValue ) / (numSamples - 1) / minValue; 
		return new AnaliseSensibilidadeValueProvider(minValue, maxValue, new Double(discr));
	}
	
	/**
	 * Discretizacao feita em intervalos nao-constantes
	 * @param minValue Valor mi�nimo
	 * @param discr Discretizacao Valor entre [0, 1]
	 * @param numSamples Numero de amostras
	 * @return
	 */
	public static ValueProvider getValueProviderDiscretizacaoAndSamples(double minValue, double discr, int numSamples){
		if(numSamples <= 0) throw new IllegalArgumentException("Numero de amostragens deve ser positivo.");
		double maxValue = minValue + (numSamples - 1) * (discr * minValue);
		return new AnaliseSensibilidadeValueProvider(minValue, maxValue, new Double(discr));
	}
	
	/**
	 * @param minValue
	 * @param maxValue
	 * @param discrer Valor entre [0, 1]
	 * @return
	 */
	public static ValueProvider getValueProviderDiscretizacao(double minValue, double maxValue, double discrer){
		if(discrer <= 0) throw new IllegalArgumentException("Discretizacao deve ser positiva.");
		return new AnaliseSensibilidadeValueProvider(minValue, maxValue, new Double(discrer));
	}
	
	private static RandomSeed createRandomSeed(){
		return new RandomSeed(){
			public double getSeed() {
				return new Random().nextDouble();
			}
		};
	}
}
