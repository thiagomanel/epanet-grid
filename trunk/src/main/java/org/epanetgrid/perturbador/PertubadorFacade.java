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
import org.epanetgrid.perturbador.perturbadores.IPerturbador;
import org.epanetgrid.perturbador.perturbadores.junctions.JunctionBaseDemandFlowPerturbador;
import org.epanetgrid.perturbador.perturbadores.junctions.JunctionElevationPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeDiameterPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeLengthPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeLossCoefPerturbador;
import org.epanetgrid.perturbador.perturbadores.pipes.PipeRoughnessCoefPerturbador;
import org.epanetgrid.perturbador.valueProvider.ValueProvider;


/**
 * @author thiago
 *
 */
public class PertubadorFacade {

	public static enum pert_types {PIPE_LENGTH, PIPE_DIAMETER, PIPE_ROUGHNESS_COEF, PIPE_LOSS_COEF, 
									JUNCTION_ELEVATION, JUNCTION_BASE_DEMAND_FLOW};
	
	private Map<String, Map<pert_types, Collection<IPerturbador>>> labelToPerturbadores = 
		new HashMap<String, Map<pert_types, Collection<IPerturbador>>>();
									
	/**
	 * 
	 * @param malhaBase
	 * @return
	 */
	public Set<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>>
				getMalhaPerturbadas(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malhaBase){
		
		
		return null;
	}
	
	/**
	 * @param varPert
	 * @param TipoPertub
	 * @return
	 */
	public void createPerturbadores(String label, ValueProvider vp, pert_types TipoPertub){
		//refactor
		IVariavelPerturbada varPert = new DefaultVariavelPerturbada(label, vp);  
		
		if(pert_types.PIPE_LENGTH.equals(TipoPertub) || pert_types.PIPE_DIAMETER.equals(TipoPertub) 
				|| pert_types.PIPE_LOSS_COEF.equals(TipoPertub) || pert_types.PIPE_ROUGHNESS_COEF.equals(TipoPertub)) {
			
			addPerturbadores(label, TipoPertub, createPipePerturbadores(varPert, TipoPertub));
		}
		else if(pert_types.JUNCTION_BASE_DEMAND_FLOW.equals(TipoPertub) || pert_types.JUNCTION_ELEVATION.equals(TipoPertub)) {
			addPerturbadores(label, TipoPertub, createJunctionPerturbadores(varPert, TipoPertub));
		}
	}
	
	/**
	 * So pode existir uma cole��o de perturbadores de determinado tipo para cada elemento.
	 * @param label
	 * @param tipoPerturbacao
	 * @param perturbadores
	 */
	private void addPerturbadores(String label, pert_types tipoPerturbacao, Collection<IPerturbador> perturbadores){
		labelToPerturbadores.remove(label);
		Map<pert_types, Collection<IPerturbador>> pertFromType = new HashMap<pert_types, Collection<IPerturbador>>();
		pertFromType.put(tipoPerturbacao, perturbadores);
		labelToPerturbadores.put(label, pertFromType);
	}
	
	private Collection<IPerturbador> createPipePerturbadores(IVariavelPerturbada varPert, pert_types TipoPerturbacao){
		
		Collection<IPerturbador> perturbadores = new LinkedList<IPerturbador>();

		//refactor
		for (int i = 0; i < varPert.getNumSamples(); i++) {
			if(pert_types.PIPE_DIAMETER.equals(TipoPerturbacao)) {
				perturbadores.add(new PipeDiameterPerturbador(varPert.getComponentLabel(), varPert.getValueProvider().getValorPerturbado()[i]));
			}
			else if(pert_types.PIPE_LENGTH.equals(TipoPerturbacao)) {
				perturbadores.add(new PipeLengthPerturbador(varPert.getComponentLabel(), varPert.getValueProvider().getValorPerturbado()[i]));
			}
			else if(pert_types.PIPE_LOSS_COEF.equals(TipoPerturbacao)) {
				perturbadores.add(new PipeLossCoefPerturbador(varPert.getComponentLabel(), varPert.getValueProvider().getValorPerturbado()[i]));
			}
			else if(pert_types.PIPE_ROUGHNESS_COEF.equals(TipoPerturbacao)) {
				perturbadores.add(new PipeRoughnessCoefPerturbador(varPert.getComponentLabel(), varPert.getValueProvider().getValorPerturbado()[i]));
			}
		}
		return perturbadores;
	}
	
	private Collection<IPerturbador> createJunctionPerturbadores(IVariavelPerturbada varPert, pert_types TipoPerturbacao){
		
		Collection<IPerturbador> perturbadores = new LinkedList<IPerturbador>();
		
		for (int i = 0; i < varPert.getNumSamples(); i++) {
			if(pert_types.JUNCTION_BASE_DEMAND_FLOW.equals(TipoPerturbacao)) {
				perturbadores.add(new JunctionBaseDemandFlowPerturbador(varPert.getComponentLabel(), varPert.getValueProvider().getValorPerturbado()[i]));
			}
			else if(pert_types.JUNCTION_ELEVATION.equals(TipoPerturbacao)) {
				perturbadores.add(new JunctionElevationPerturbador(varPert.getComponentLabel(), varPert.getValueProvider().getValorPerturbado()[i]));
			}
		}
		return perturbadores;
	}
	
}
