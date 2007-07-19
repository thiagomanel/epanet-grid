package org.epanetgrid.relatorios;

import java.io.File;

import org.epanetgrid.relatorios.IAlarme.Tipo;

/**
 * @author
 * since 18/07/2007
 */
public class DefaultOutPutRelatorio implements IOutPutRelatorio{

	private final File arquivo;

	public DefaultOutPutRelatorio(File arquivo) {
		this.arquivo = arquivo;
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#getNumAlarmes(org.epanetgrid.relatorios.IAlarme.Tipo)
	 */
	public int getNumAlarmes(Tipo tipo) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#getNumTotalAlarmes()
	 */
	public int getNumTotalAlarmes() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#pressaoMinimaNode()
	 */
	public PressaoMinimaNode pressaoMinimaNode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#pressaoMaximaNode()
	 */
	public PressaoMaximaNode pressaoMaximaNode() {
		return null;
	}

	

}
