/**
 * 
 */
package org.epanetgrid.resultado;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;

/**
 * @author alan
 *
 */
public class ResultadoSimulacao {

	private double custoDeEnergia;
	private ICenario[] cenarios;
	private NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malha;
	
	public ResultadoSimulacao(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> malha, 
			double custo, ICenario...cenarios) {
		this.malha = malha;
		this.cenarios = cenarios;
		this.custoDeEnergia = custo;
	}

	/**
	 * @return the cenarios
	 */
	public ICenario[] getCenarios() {
		return cenarios;
	}

	/**
	 * @return the custoDeEnergia
	 */
	public double getCustoDeEnergia() {
		return custoDeEnergia;
	}

	/**
	 * @return the malha
	 */
	public NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> getMalha() {
		return malha;
	}

}
