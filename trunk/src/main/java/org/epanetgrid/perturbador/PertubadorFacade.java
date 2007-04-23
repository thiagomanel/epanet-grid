/**
 * 
 */
package org.epanetgrid.perturbador;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @author thiago
 *
 */
public class PertubadorFacade {
	
	private Map<String, Map<PertubationType, Collection<IPerturbador>>> labelToPerturbadores = 
		new HashMap<String, Map<PertubationType, Collection<IPerturbador>>>();
									
	/**
	 * 
	 * @param malhaBase
	 * @return
	 */
	public List<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>>
				getMalhaPerturbadas(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malhaBase){
		
		return new PertubadorRunner().getMalhaPerturbadas(malhaBase, labelToPerturbadores);
	}
	
	/**
	 * @param varPert
	 * @param TipoPertub
	 * @return
	 */
	public void createPerturbadores(String label, ValueProvider vp, PertubationType TipoPertub){
		//refactor
		IVariavelPerturbada varPert = new DefaultVariavelPerturbada(label, vp);  
		addPerturbadores(label, TipoPertub, createPerturbadores(varPert, TipoPertub));
	}
	
	/**
	 * So pode existir uma cole��o de perturbadores de determinado tipo para cada elemento.
	 * @param label
	 * @param tipoPerturbacao
	 * @param perturbadores
	 */
	private void addPerturbadores(String label, PertubationType tipoPerturbacao, Collection<IPerturbador> perturbadores){
		labelToPerturbadores.remove(label);
		Map<PertubationType, Collection<IPerturbador>> pertFromType = new HashMap<PertubationType, Collection<IPerturbador>>();
		pertFromType.put(tipoPerturbacao, perturbadores);
		labelToPerturbadores.put(label, pertFromType);
	}
	
	private List<IPerturbador> createPerturbadores(IVariavelPerturbada varPert, PertubationType tipoPerturbacao){
		
		List<IPerturbador> perturbadores = new LinkedList<IPerturbador>();
		for (int i = 0; i < varPert.getNumSamples(); i++) {
			perturbadores.add(PertubationType.getPerturbador(varPert.getComponentLabel(),
																varPert.getValueProvider().getValorPerturbado()[i],
																tipoPerturbacao));
		}
		
		return perturbadores;
	}
	
}
