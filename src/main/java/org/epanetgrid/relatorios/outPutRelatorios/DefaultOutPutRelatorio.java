package org.epanetgrid.relatorios.outPutRelatorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.quantities.Pressure;

import org.epanetgrid.relatorios.common.IDocItem;
import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
import org.epanetgrid.relatorios.common.RegionMatcher;
import org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo;
import org.jscience.physics.measures.Measure;

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
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 18/07/2007
 */
public class DefaultOutPutRelatorio implements IOutPutRelatorio{

	private final LinedTextFileDoc linedText;
	private final IMatcher totalAlarmesMatcher;
	private final IMatcher alarmePressaoNegativaMatcher;
	private final IMatcher pressaoMatcher;

	private Map<IMatcher, Collection<IDocItem>> docItems; //final

	/**
	 * @param linedText
	 * @param totalAlarmesMatcher
	 * @param pressaoNegativaMatcher
	 */
	private DefaultOutPutRelatorio(LinedTextFileDoc linedText, IMatcher totalAlarmesMatcher, 
										IMatcher pressaoNegativaMatcher,
										IMatcher pressaoMatcher) {
		
		this.linedText = linedText;
		this.totalAlarmesMatcher = totalAlarmesMatcher;
		this.alarmePressaoNegativaMatcher = pressaoNegativaMatcher; 
		this.pressaoMatcher = pressaoMatcher; 

		if(linedText == null) throw new IllegalArgumentException("The linedText must not be null");
		
		//fazer o parse uma vez apenas
		try {
			this.docItems = this.linedText.getDocItems();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#getNumAlarmes(org.epanetgrid.relatorios.IAlarme.Tipo)
	 */
	public int getNumAlarmes(Tipo tipo) {
		return (alarmePressaoNegativaMatcher != null ? docItems.get(alarmePressaoNegativaMatcher).size() : 0 );
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#getNumTotalAlarmes()
	 */
	public int getNumTotalAlarmes() {
		//se naum houve matche deve existir uma colceao vazia
		return (totalAlarmesMatcher != null ? docItems.get(totalAlarmesMatcher).size() : 0 ) ;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#pressaoMinimaNode()
	 */
	public PressaoNode pressaoMinimaNode() {
		List<PressaoNode> pressoes = parsePressoes(docItems.get(pressaoMatcher));
		Collections.sort(pressoes);
		return pressoes.get(0);//optimize
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#pressaoMaximaNode()
	 */
	public PressaoNode pressaoMaximaNode() {
		List<PressaoNode> pressoes = parsePressoes(docItems.get(pressaoMatcher));
		Collections.sort(pressoes);
		return pressoes.get(pressoes.size() - 1);//optimize
	}
	
	private List<PressaoNode> parsePressoes(Collection<IDocItem> collection) {
		
		List<PressaoNode> pressoes = new LinkedList<PressaoNode>();
		
		for (IDocItem docItem : collection) {
			pressoes.add(parsePressao(docItem.getSource()));
		}
		
		return pressoes;
	}

	/**
	 * Seguindo o padrao definido pelo EPANET, o primeiro token eh a descricao
	 * do elemento e o quarto token eh a pressao 
	 */
	private PressaoNode parsePressao(String source) {

		StringTokenizer tokenizer = new StringTokenizer(source);

		String node = tokenizer.nextToken();

		tokenizer.nextToken();//ignored
		tokenizer.nextToken();
		String pressure = tokenizer.nextToken();
		
		return new PressaoNode(Measure.valueOf(Long.parseLong(pressure), Pressure.SI_UNIT), node);
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
			
			RegexMatcher pressaoNegativaMatcher = null;
			if (pressaoNegativaAlarmPattern != null) {
				pressaoNegativaMatcher = new RegexMatcher(pressaoNegativaAlarmPattern);
				linedTextBuilder.addMatcher(pressaoNegativaMatcher);
			}
			
			IMatcher pressaoMaximaMinimaMatcher = new RegionMatcher.Builder().build();
			
			return new DefaultOutPutRelatorio(linedTextBuilder.build(), totalAlarmsRegexMatcher, pressaoNegativaMatcher, 
					pressaoMaximaMinimaMatcher);
		}
	}

}
