/**
 * 
 */
package org.epanetgrid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
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
import org.epanetgrid.perturbador.PerturbationType;
import org.epanetgrid.perturbador.valueProvider.SimpleValueProvider;
import org.epanetgrid.perturbador.valueProvider.ValueProvider;
import org.epanetgrid.relatorios.outPutRelatorios.EPANETOutPutRelatorio;
import org.epanetgrid.relatorios.outPutRelatorios.IOutPutRelatorio;
import org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo;
import org.epanetgrid.relatorios.resultRelatorios.EPANETResultRelatorio;
import org.epanetgrid.relatorios.resultRelatorios.IResultRelatorio;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 25/08/2007
 */
public class AcceptFacade {

	private PertubadorFacade perturbFacade = new PertubadorFacade();
	private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;
	private List<NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>>> malhasGeradas;
	private File outputfile;
	private IOutPutRelatorio outputRelatorio;
	private IResultRelatorio resultRelatorio;
	
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
		PerturbationType type = getTypeByString(propriedade);
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
	
	private PerturbationType getTypeByString(String typeDescrip){
		if("TANK_ELEVATION".endsWith(typeDescrip)) {//so pra passar
			return PerturbationType.TANK_ELEVATION;
		}
		return null;
	}
	
	/** Teste gerador de relatórios */ 
	 
	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void setArquivoSaida(String file) throws FileNotFoundException{
		this.outputfile = new File(file);
		try {
			this.outputRelatorio= new EPANETOutPutRelatorio().generateOutPutRelatorio(outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setArquivoResultado(String file) throws IOException, ParseException{
		try {
			this.resultRelatorio = new EPANETResultRelatorio().generateRelatorio(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getNodePressaoMinima(){
		return outputRelatorio.pressaoMinimaNode().getNodeName();
	}
	
	public double getPressaoMinima(){
		return outputRelatorio.pressaoMinimaNode().getPressao().getEstimatedValue();
	}
	
	public String getNodePressaoMaxima(){
		return outputRelatorio.pressaoMaximaNode().getNodeName();
	}
	
	public double getPressaoMaxima(){
		return outputRelatorio.pressaoMinimaNode().getPressao().getEstimatedValue();
	}
	
	public String getElementVelocidadeMinima(){
		return outputRelatorio.velocidadeMinimaNode().getNodeName();
	}
	
	public String getElementVelocidadeMaxima(){
		return outputRelatorio.velocidadeMaximaNode().getNodeName();
	}
	
	public double getVelocidadeMaxima(){
		return outputRelatorio.velocidadeMinimaNode().getVelocidade().getEstimatedValue();
	}
	
	public double getVelocidadeMinima(){
		return outputRelatorio.velocidadeMaximaNode().getVelocidade().getEstimatedValue();
	}
	
	public int  getTotalAlarmes(){
		return outputRelatorio.getNumTotalAlarmes();
	}

	public int getDiaSemanaSimulacao(){
		return resultRelatorio.getDataSimulacao().getDay();
	}
	
	public int getMesSimulacao(){
		return resultRelatorio.getDataSimulacao().getMonth();
	}
	
	public int getDiaSimulacao(){
		return resultRelatorio.getDataSimulacao().getDate();
	}
	
	public int getHoraSimulacao(){
		return resultRelatorio.getDataSimulacao().getHours();
	}
	
	public int getMinSimulacao(){
		return resultRelatorio.getDataSimulacao().getMinutes();
	}
	
	public int getSecsSimulacao(){
		return resultRelatorio.getDataSimulacao().getSeconds();
	}
	
	public int getAnoSimulacao(){
		return resultRelatorio.getDataSimulacao().getYear()+1900;
	}

	public double getCustoTotal(){
		return resultRelatorio.getCusto().getEstimatedValue();
	}

	public int getNumAlarmesPressaoNegativa(){
		return outputRelatorio.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO);
	}
	
}
