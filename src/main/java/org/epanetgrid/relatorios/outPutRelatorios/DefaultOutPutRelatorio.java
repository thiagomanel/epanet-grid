package org.epanetgrid.relatorios.outPutRelatorios;

import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo;

/**
 * Este relatorio contem informacoes sobre:
 * 	
 * 	Quantidade de alarmes de pressao negativa
 *	Quantidade total de alarmes
 *	Presso minima nos nos em toda a simulacao
 *	Pressao maxima por no em toda a simulacao 
 *	Velocidade minima dos dutos em toda a simulacao
 *	Velocidade maxima dos dutos em toda a simulacao
 * 
 * @author
 * since 18/07/2007
 */
public class DefaultOutPutRelatorio implements IOutPutRelatorio{

	private final LinedTextFileDoc.Builder linedTextBuilder;

	/**
	 * @param linedTextBuilder
	 */
	private DefaultOutPutRelatorio(LinedTextFileDoc.Builder linedTextBuilder) {
		
		if(linedTextBuilder == null) throw new IllegalArgumentException("The linedText must not be null");
		
		this.linedTextBuilder = linedTextBuilder;
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

	/**
	 * Os padroes setados como <code>null</code> serao ignorados.
	 * 
	 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
	 * since 07/08/2007
	 */
	public static class Builder{
		
		private String pressaoMinimaPattern;
		private String pressaoMaximaPattern;
		private String totalAlarmesPattern;
		private String pressaoNegativaAlarmPattern;
		
		/**
		 * @param pressaoMinimaPattern
		 * @return
		 */
		public Builder setPressaoMinimaPattern(String pressaoMinimaPattern){
			//test null;
			this.pressaoMaximaPattern = pressaoMinimaPattern;
			return this;
		}

		/**
		 * @param pressaoMaximaPattern
		 * @return
		 */
		public Builder setPressaoMaximaPattern(String pressaoMaximaPattern) {
			this.pressaoMaximaPattern = pressaoMaximaPattern;
			return this;
		}

		/**
		 * @param totalAlarmesPattern
		 * @return
		 */
		public Builder setTotalAlarmesPattern(String totalAlarmesPattern) {
			this.totalAlarmesPattern = totalAlarmesPattern;
			return this;
		}

		/**
		 * @param pressaoNegativaAlarmPattern
		 * @return
		 */
		public Builder setPressaoNegativaAlarmPattern(String pressaoNegativaAlarmPattern) {
			this.pressaoNegativaAlarmPattern = pressaoNegativaAlarmPattern;
			return this;
		}
		
		/**
		 * Constroi um <code>DefaultOutPutRelatorio</code> caso algum padrao valido tenha sido
		 * atribuido, caso contrario lanca <code>IllegalStateException</code>.
		 * @param linedTextBuilder
		 * @return
		 */
		public DefaultOutPutRelatorio build(LinedTextFileDoc.Builder linedTextBuilder){
			
			if( (this.pressaoMaximaPattern != null ) ||
					(this.pressaoMinimaPattern != null) ||
					(this.pressaoNegativaAlarmPattern != null) ||
					(this.totalAlarmesPattern != null) ) {
				
				return new DefaultOutPutRelatorio(linedTextBuilder);
			}
			
			throw new IllegalStateException("Nenhum padrao foi atribuido");
		}
	}

}
