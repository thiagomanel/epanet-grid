package org.epanetgrid.relatorios.outPutRelatorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.epanetgrid.relatorios.common.IDocItem;
import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
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

	private final LinedTextFileDoc linedText;
	private final IMatcher totalAlarmesMatcher;
	private Map<IMatcher, Collection<IDocItem>> docItems; //final

	/**
	 * @param linedText
	 */
	private DefaultOutPutRelatorio(LinedTextFileDoc linedText, IMatcher totalAlarmesMatcher) {
		
		this.linedText = linedText;
		this.totalAlarmesMatcher = totalAlarmesMatcher;

		if(linedText == null) throw new IllegalArgumentException("The linedText must not be null");
		
		//fazer o parse uma vez apenas
		try {
			this.docItems = this.linedText.getDocItems();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//se naum houve matche deve existir uma colceao vazia
		return (this.totalAlarmesMatcher != null ? docItems.get(totalAlarmesMatcher).size() : 0 ) ;
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
		 * @throws FileNotFoundException 
		 */
		public DefaultOutPutRelatorio build(LinedTextFileDoc.Builder linedTextBuilder) throws FileNotFoundException{

			
			
			if( (this.pressaoMaximaPattern != null ) ||
					(this.pressaoMinimaPattern != null) ||
					(this.pressaoNegativaAlarmPattern != null) ||
					(this.totalAlarmesPattern != null) ) {
				
				if(linedTextBuilder == null) throw new IllegalArgumentException("The linedTextBuilder must not be null");
				
				if (pressaoMaximaPattern != null) {
					linedTextBuilder.addMatcher(new RegexMatcher(pressaoMaximaPattern));
				}
				
				if (pressaoMinimaPattern != null) {
					linedTextBuilder.addMatcher(new RegexMatcher(pressaoMinimaPattern));
				}
				
				if (pressaoNegativaAlarmPattern != null) {
					linedTextBuilder.addMatcher(new RegexMatcher(pressaoNegativaAlarmPattern));
				}
				
				RegexMatcher totalAlarmsRegexMatcher = null;
				
				if (totalAlarmesPattern != null) {
					totalAlarmsRegexMatcher = new RegexMatcher(totalAlarmesPattern);
					linedTextBuilder.addMatcher(totalAlarmsRegexMatcher);
				}
				
				return new DefaultOutPutRelatorio(linedTextBuilder.build(), totalAlarmsRegexMatcher);
			}
			
			throw new IllegalStateException("Nenhum padrao foi atribuido");
		}
	}

}
