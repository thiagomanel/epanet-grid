/**
 * 
 */
package org.epanetgrid.perturbador;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

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
	
	/**
	 * @param malhaBase
	 * @param vars
	 * @return
	 */
	public Set<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>>
				getMalhaPerturbadas(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malhaBase, 
									Collection<IPerturbador> vars){
		
		return null;
	}
	
	/**
	 * @param label
	 * @param tipoPerturbacao
	 * @param vp
	 * @return
	 */
	public IVariavelPerturbada createPerturbacao(String label, ValueProvider vp){
		return new DefaultVariavelPerturbada(label, vp);
	}
	
	/**
	 * @param varPert
	 * @param TipoPertub
	 * @return
	 */
	public Collection<IPerturbador> createPerturbadores(IVariavelPerturbada varPert, pert_types TipoPertub){
		//refactor
		if(pert_types.PIPE_LENGTH.equals(TipoPertub) || pert_types.PIPE_DIAMETER.equals(TipoPertub) 
				|| pert_types.PIPE_LOSS_COEF.equals(TipoPertub) || pert_types.PIPE_ROUGHNESS_COEF.equals(TipoPertub)) {
			
			return createPipePerturbadores(varPert, TipoPertub);
		}
		else if(pert_types.JUNCTION_BASE_DEMAND_FLOW.equals(TipoPertub) || pert_types.JUNCTION_ELEVATION.equals(TipoPertub)) {
			return createJunctionPerturbadores(varPert, TipoPertub);
		}
		
		return null;
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
