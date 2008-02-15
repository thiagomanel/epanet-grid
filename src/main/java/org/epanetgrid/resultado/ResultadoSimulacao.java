/**
 * 
 */
package org.epanetgrid.resultado;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.quantities.Pressure;
import javax.quantities.Velocity;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.relatorios.outPutRelatorios.PressaoNode;
import org.epanetgrid.relatorios.outPutRelatorios.VelocidadeNode;
import org.epanetgrid.resultado.output.AlarmeSaida;
import org.epanetgrid.resultado.output.DefaultErrorRelatorio;
import org.epanetgrid.resultado.output.DefaultOutPutRelatorio;
import org.epanetgrid.resultado.output.EPANETErrorRelatorio;
import org.epanetgrid.resultado.output.EPANETOutPutRelatorio;
import org.epanetgrid.resultado.output.IAlarme;
import org.epanetgrid.resultado.output.IInputError;
import org.epanetgrid.util.NetWorkUtils;

/**
 * @author alan
 *
 */
public class ResultadoSimulacao {

	private DefaultOutPutRelatorio relatorioFinal;
	private DefaultErrorRelatorio relatorioSaida;
	
	private List<Set<IAlarme>> alarmes;
	private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network;
	private int intervalos;
	
	public ResultadoSimulacao(String relatorioFilePath, String saidaFilePath, 
			NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> network) throws FileNotFoundException {
		this.network = network;
		this.intervalos = (int)(network.getDuration().getMillis() / network.getHydraulicTimestep().getMillis());
		EPANETOutPutRelatorio relatorio = new EPANETOutPutRelatorio();
		this.relatorioFinal = relatorio.generateOutPutRelatorio(new File(relatorioFilePath));
		this.relatorioSaida = new EPANETErrorRelatorio().generateOutPutRelatorio(new File(saidaFilePath));
		this.alarmes = capturarAlarmes();
	}

	public boolean executionHalted() {
		return this.relatorioSaida.executionHalted();
	}

	private List<Set<IAlarme>> capturarAlarmes() {
		
		List<Set<IAlarme>> alarmes = getWarnings(this.relatorioSaida.getAlarmes());
		
		List<Map<String, VelocidadeNode>> velocidades = relatorioFinal.velocidadeNodes();
		List<Map<String, PressaoNode>> pressoesNiveis = relatorioFinal.pressaoNodes();
		
		for (int i = 0; i < velocidades.size(); i++) {
			
			Map<String, VelocidadeNode> resultDutos = velocidades.get(i);
			Map<String, PressaoNode> resultTanquesNos = pressoesNiveis.get(i);
			
//			Set<IAlarme> alarmesIntervalo = new HashSet<IAlarme>();
			
			for (IPipe<?> pipe : network.getPipes()) {
				VelocidadeNode result = resultDutos.get(pipe.label());
				if ( result != null ){
					if( pipe.getMaxVelocity() != null &&
							result.getVelocidade().compareTo(pipe.getMaxVelocity()) > 0 ) {
						IAlarme alarme = new AlarmeSaida(null, 
								"A velocidade no duto <" + pipe.label() + "> está maior que a máxima.", 
								Velocity.SI_UNIT.getDimension(), IAlarme.Tipo.max);
						alarmes.get(i).add(alarme);
					} else if( pipe.getMinVelocity() != null && 
							result.getVelocidade().compareTo(pipe.getMinVelocity()) < 0 ) {
						IAlarme alarme = new AlarmeSaida(null, 
								"A velocidade no duto <" + pipe.label() + "> está menor que a mínima.", 
								Velocity.SI_UNIT.getDimension(), IAlarme.Tipo.min);
						alarmes.get(i).add(alarme);
					}
				}
			}
			
			for (IJunction<?> junction : network.getJunctions()) {
				PressaoNode result = resultTanquesNos.get(junction.label());
				if ( result != null ){
					if( junction.getMaxPressure()!= null && 
							result.getPressao().compareTo(junction.getMaxPressure()) > 0 ) {
						IAlarme alarme = new AlarmeSaida(null, 
								"A pressão na junção <" + junction.label() + "> está maior que a máxima.", 
								Pressure.SI_UNIT.getDimension(), IAlarme.Tipo.max);
						alarmes.get(i).add(alarme);
					} else if( junction.getMinPressure() != null &&
							result.getPressao().compareTo(junction.getMinPressure()) < 0 ) {
						IAlarme alarme = new AlarmeSaida(null, 
								"A pressão na junção <" + junction.label() + "> está menor que a mínima.", 
								Pressure.SI_UNIT.getDimension(), IAlarme.Tipo.min);
						alarmes.get(i).add(alarme);
					}
				}
			}
			
			for (ITank<?> tank : network.getTanks()) {
				PressaoNode result = resultTanquesNos.get(tank.label());
				if ( result != null ){
					if( tank.getMaximumSecurityLevel() != null && 
							result.getPressao().getEstimatedValue() > tank.getMaximumSecurityLevel().getEstimatedValue() ) {
						IAlarme alarme = new AlarmeSaida(null, 
								"O nível do tanque <" + tank.label() + "> está maior que o máximo.", 
								Pressure.SI_UNIT.getDimension(), IAlarme.Tipo.max);
						alarmes.get(i).add(alarme);
					} else if( tank.getMinimumSecurityLevel() != null && 
							result.getPressao().getEstimatedValue() < tank.getMinimumSecurityLevel().getEstimatedValue() ) {
						IAlarme alarme = new AlarmeSaida(null, 
								"O nível do tanque <" + tank.label() + "> está menor que o mínimo.", 
								Pressure.SI_UNIT.getDimension(), IAlarme.Tipo.min);
						alarmes.get(i).add(alarme);
					}
				}
			}
			
//			alarmes.add(alarmesIntervalo);
			
			
		}
		
		return alarmes;
	}


	private List<Set<IAlarme>> getWarnings(List<IAlarme> alarmes) {
		
		List<Set<IAlarme>> warnings = new LinkedList<Set<IAlarme>>();
		
		for (int i = 0; i <= intervalos; i++) {
			warnings.add(new HashSet<IAlarme>());
		}
		
		for (IAlarme alarme : alarmes) {
			if ( alarme.getTipoDeRestricao().equals(IAlarme.Tipo.warning) ) {
//				int index = getIndiceWarning(alarme);
				int index = NetWorkUtils.getIndice(network, alarme.getDate());
//				if (index == intervalos) {
//					index--;
//				}
				warnings.get(index).add(alarme);
			}
		}
		
		return warnings;
	}


//	private int getIndiceWarning(IAlarme alarme) {
//		long d1 = (network.getStartClockTime().getHour() * 60 * 60 * 1000) + 
//				(network.getStartClockTime().getMinutes() * 60 * 1000);
//
//		long d2 = (alarme.getDate().getHour() * 60 * 60 * 1000) + 
//				(alarme.getDate().getMinutes() * 60 * 1000);
//		
//		return (int)( (d2 - d1) / network.getHydraulicTimestep().getMillis() );
//	}

	public DefaultOutPutRelatorio getRelatorioFinal() {
		return relatorioFinal;
	}
	
	public DefaultErrorRelatorio getRelatorioSaida() {
		return relatorioSaida;
	}

	public List<IInputError> getInputErrors() {
		return relatorioSaida.getInputErrors();
	}
	
	/**
	 * @return the custoDeEnergia
	 */
	public double getCustoDeEnergia() {
		return this.relatorioFinal.getCusto().getEstimatedValue();
	}
	
	public List<Set<IAlarme>> getAlarmes() {
		return alarmes;
	}
	
	public NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> getNetwork() {
		return network;
	}

}
