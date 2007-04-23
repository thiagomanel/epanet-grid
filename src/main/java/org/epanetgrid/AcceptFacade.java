/**
 * 
 */
package org.epanetgrid;

import java.io.IOException;
import java.util.List;

import org.epanetgrid.data.DataFacade;
import org.epanetgrid.model.NetworkComponent;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.perturbador.PertubadorFacade;
import org.epanetgrid.perturbador.PertubationType;
import org.epanetgrid.perturbador.valueProvider.SimpleValueProvider;
import org.epanetgrid.perturbador.valueProvider.ValueProvider;
import org.jscience.physics.measures.Measure;

/**
 * @author thiagoepdc
 *
 */
public class AcceptFacade {

	private PertubadorFacade perturbFacade = new PertubadorFacade();
	private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;
	private List<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>> malhasGeradas;
	
	/**
	 * @param file
	 * @throws IOException 
	 */
	public void setMalhaEntrada(String file) throws IOException{
		this.network = createNetWork(file);
	}

	/**
	 * @param elemento
	 * @param propriedade
	 * @param valorMinimo
	 * @param discretizacao
	 * @param quantidadeMalhas
	 */
	public void addElementoPerturbado(String elemento, String propriedade, double valorMinimo, 
										double discretizacao, int quantidadeMalhas){
		
		ValueProvider vp = SimpleValueProvider.getValueProviderDiscretizacaoAndSamples(valorMinimo, discretizacao, quantidadeMalhas);
		PertubationType type = getTypeByString(propriedade);
		perturbFacade.createPerturbadores(elemento, vp, type);
	}
	
	/**
	 * @param elemento
	 * @param propriedade
	 * @param value
	 * @return
	 */
	public boolean containsValue(String elemento, String propriedade, double value){
		return containsValue(malhasGeradas, elemento, propriedade, value);
	}
	
	private boolean containsValue(List<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>> 
										malhasGeradas, String elemento, String propriedade, double value){
		for (NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork : malhasGeradas) {
			if(containsValue(netWork, elemento, propriedade, value)){
				return true;
			}
		}
		return false;
	}
	
	private boolean containsValue(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> 
										netWork, String elemento, String propriedade, double value) {
		
		NetworkComponent component = netWork.getElemento(elemento);
		if(netWork.getTanks().contains(component)) {
			if("TANK_ELEVATION".equals(propriedade)) {
				Measure elevation = ((ITank<?>)component).getElevation(); 
				return elevation.approximates(Measure.valueOf(value, elevation.getUnit()));
			}
		}
		
		throw new IllegalArgumentException();
	}

	/**
	 * 
	 */
	public void geraPerturbacao(){
		malhasGeradas = perturbFacade.getMalhaPerturbadas(this.network);
	}
	
	/**
	 * @return
	 */
	public int numMalhasGeradas(){
		return this.malhasGeradas.size();
	}
	
	private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> createNetWork(String file) throws IOException {
		return new DataFacade().createNetWorkFromFile(file);
	}
	
	private PertubationType getTypeByString(String typeDescrip){
		if("TANK_ELEVATION".endsWith(typeDescrip)) {//so pra passar
			return PertubationType.TANK_ELEVATION;
		}
		return null;
	}
}
