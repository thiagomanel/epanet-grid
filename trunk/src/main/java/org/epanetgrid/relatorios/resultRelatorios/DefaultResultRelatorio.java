package org.epanetgrid.relatorios.resultRelatorios;

import java.io.File;
import java.util.Date;

import org.jscience.economics.money.Money;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 18/07/2007
 */
public class DefaultResultRelatorio implements IResultRelatorio{

	private final File arquivo;
	
	/**
	 * @param arquivo
	 */
	public DefaultResultRelatorio(File arquivo) {
		this.arquivo = arquivo;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IResultRelatorio#getDataSimulacao()
	 */
	public Date getDataSimulacao() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IResultRelatorio#getCusto()
	 */
	public Measure<Money> getCusto() {
		return null;
	}


}
