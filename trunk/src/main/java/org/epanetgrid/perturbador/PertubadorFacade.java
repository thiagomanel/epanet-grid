/**
 * 
 */
package org.epanetgrid.perturbador;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.perturbador.perturbadores.IPerturbador;
import org.epanetgrid.perturbador.valueProvider.ValueProvider;


/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
public class PertubadorFacade {
	
	private final Map<String, Map<PerturbationType, Collection<IPerturbador>>> labelToPerturbadores = 
		new HashMap<String, Map<PerturbationType, Collection<IPerturbador>>>();
									
	/**
	 * Gera
	 * @param malhaBase
	 * @return
	 */
	public List<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>>
				getMalhaPerturbadas(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malhaBase){

		return new PertubadorRunner().getMalhaPerturbadas(malhaBase, labelToPerturbadores);
	}
	
	/**
	 * Cria perturbadores para um determinador componente da rede.
	 * @param label Identificador do componente da rede
	 * @param vp Gerador de valores para a perturbacao
	 * @param TipoPertub Tipo da perturbacao
	 */
	public void createPerturbadores(String label, ValueProvider vp, PerturbationType TipoPertub){
		//refactor
		IVariavelPerturbada varPert = new DefaultVariavelPerturbada(label, vp);  
		addPerturbadores(label, TipoPertub, createPerturbadores(varPert, TipoPertub));
	}
	
	/**
	 * So pode existir uma colecao de perturbadores de determinado tipo para cada elemento.
	 * @param label
	 * @param tipoPerturbacao
	 * @param perturbadores
	 */
	private void addPerturbadores(String label, PerturbationType tipoPerturbacao, Collection<IPerturbador> perturbadores){
		labelToPerturbadores.remove(label);
		Map<PerturbationType, Collection<IPerturbador>> pertFromType = new HashMap<PerturbationType, Collection<IPerturbador>>();
		pertFromType.put(tipoPerturbacao, perturbadores);
		labelToPerturbadores.put(label, pertFromType);
	}
	
	/**
	 * @param varPert
	 * @param tipoPerturbacao
	 * @return
	 */
	private List<IPerturbador> createPerturbadores(IVariavelPerturbada varPert, PerturbationType tipoPerturbacao){
		
		List<IPerturbador> perturbadores = new LinkedList<IPerturbador>();
		for (int i = 0; i < varPert.getNumSamples(); i++) {
			perturbadores.add(PerturbationType.getPerturbador(varPert.getComponentLabel(), 
																varPert.getValueProvider().getValorPerturbado()[i], tipoPerturbacao));
		}
		
		return perturbadores;
	}
	
}
