/**
 * 
 */
package org.epanetgrid.perturbador;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.epanetgrid.model.NetworkComponent;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.perturbador.PertubadorFacade.pert_types;
import org.epanetgrid.perturbador.perturbadores.IPerturbador;

/**
 * Executor da perturbação.
 * 
 * @author thiagoepdc
 */
class PertubadorRunner {

	/**
	 * 
	 * @param malhaBase Malha de referência de onde será executada a pertubação.
	 * @param labelToPerturbadores Mapas que relacionam os elementos da malha e os
	 * perturbadores que serão executados para cada um dos elementos.
	 *  
	 * @return Conjunto de malhas resultantes da perturbação.
	 */
	public Set<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>>
				getMalhaPerturbadas(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malhaBase, 
									Map<String, Map<pert_types, Collection<IPerturbador>>> labelToPerturbadores ){
		
		
		Map<String, Collection<NetworkComponent>> elementosGerados = new HashMap<String, Collection<NetworkComponent>>(); 
		
		for (String label : labelToPerturbadores.keySet()) {
			Map<pert_types, Collection<IPerturbador>> perturbadoresByType = labelToPerturbadores.get(label);
			elementosGerados.put(label, geraElementosPerturbados(malhaBase.getElemento(label), perturbadoresByType));
		}
		
		
		return null;
	}
	
	/**
	 * 
	 * @param component
	 * @param perturbadoresByType
	 * @return
	 */
	protected Collection<NetworkComponent> geraElementosPerturbados(NetworkComponent component,
									Map<pert_types, Collection<IPerturbador>> perturbadoresByType) {
		
		Collection<NetworkComponent> elementosPerturbados = new LinkedList<NetworkComponent>();
		for (Collection<IPerturbador> perturbadores : perturbadoresByType.values()) {
			elementosPerturbados.addAll(disturbComponent(component, perturbadores));
		}
		return elementosPerturbados;
	}
	
	/**
	 * 
	 * @param component
	 * @param perturbadores
	 * @return
	 */
	protected Collection<NetworkComponent> disturbComponent(NetworkComponent component, Collection<IPerturbador> perturbadores){
		
		Collection<NetworkComponent> elementosPerturbados = new LinkedList<NetworkComponent>();
		for (IPerturbador perturbador : perturbadores) {
			elementosPerturbados.add(perturbador.disturb(component));
		}
		return elementosPerturbados;
	}
	
	private Set<NetWork> createMalhasPerturbadas(){
		return null;
	}
	
}
