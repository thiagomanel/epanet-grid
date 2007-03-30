/**
 * 
 */
package org.epanetgrid.perturbador;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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

/**
 * @author thiagoepdc
 *
 */
class PertubadoRunner {

	public Set<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>>
				getMalhaPerturbadas(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malhaBase, 
						Collection<IPerturbador> vars){
		
		return null;
	}
	
	private Map<String, Collection<IPerturbador>> filterByComponent(Collection<IPerturbador> perturbadores){
		
		Map<String, Collection<IPerturbador>> perturbadoresByComponent = new HashMap<String, Collection<IPerturbador>>();
		
		for (IPerturbador perturbador : perturbadores) {
			String componentLabel = perturbador.getComponentLabel();
			Collection<IPerturbador> perts = perturbadoresByComponent.get(componentLabel);
			if(perts == null) {
				perts = new LinkedList<IPerturbador>();
			}
			perts.add(perturbador);
		}
		
		return perturbadoresByComponent;
	}
}
