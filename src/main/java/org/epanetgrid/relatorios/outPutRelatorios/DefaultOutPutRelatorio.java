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
import javax.quantities.Velocity;

import org.epanetgrid.relatorios.common.IDocItem;
import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
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
	private final IMatcher velocidadeMatcher;

	private Map<IMatcher, Collection<IDocItem>> docItems; //final

	/**
	 * @param linedText
	 * @param totalAlarmesMatcher
	 * @param pressaoNegativaMatcher
	 * @param velocidadeMatcher 
	 */
	private DefaultOutPutRelatorio(LinedTextFileDoc linedText, IMatcher totalAlarmesMatcher, 
										IMatcher pressaoNegativaMatcher,
										IMatcher pressaoMatcher, IMatcher velocidadeMatcher) {
		
		this.linedText = linedText;
		this.totalAlarmesMatcher = totalAlarmesMatcher;
		this.alarmePressaoNegativaMatcher = pressaoNegativaMatcher; 
		this.pressaoMatcher = pressaoMatcher;
		this.velocidadeMatcher = velocidadeMatcher;

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
		
		if(alarmePressaoNegativaMatcher == null) throw new IllegalStateException("A IMatcher should be assigned for" +
			"use the getNumAlarmes method. See the Builder methods");

		Collection<IDocItem> alarmes = docItems.get(alarmePressaoNegativaMatcher);
	
		return (alarmes != null) ? alarmes.size() : 0;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#getNumTotalAlarmes()
	 */
	public int getNumTotalAlarmes() {
		
		if(totalAlarmesMatcher == null) throw new IllegalStateException("A IMatcher should be assigned for" +
				"use the getNumTotalAlarmes method. See the Builder methods");
		
		Collection<IDocItem> alarmes = docItems.get(totalAlarmesMatcher);
			
		return (alarmes != null) ? alarmes.size() : 0;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.outPutRelatorios.IOutPutRelatorio#velocidadeMaximaNode()
	 */
	public VelocidadeNode velocidadeMaximaNode() {
		List<VelocidadeNode> velocidades = parseVelocidades(docItems.get(velocidadeMatcher));
		Collections.sort(velocidades);
		return velocidades.get(velocidades.size() - 1);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.outPutRelatorios.IOutPutRelatorio#velocidadeMinimaNode()
	 */
	public VelocidadeNode velocidadeMinimaNode() {
		List<VelocidadeNode> velocidades = parseVelocidades(docItems.get(velocidadeMatcher));
		Collections.sort(velocidades);
		return velocidades.get(0);
	}
	
	/**
	 * @param collection
	 * @return
	 */
	private List<VelocidadeNode> parseVelocidades(Collection<IDocItem> collection) {
		
		List<VelocidadeNode> velocidades = new LinkedList<VelocidadeNode>();
		
		for (IDocItem docItem : collection) {
			velocidades.add(parseVelocidade(docItem.getSource()));
		}
		
		return velocidades;
	}

	/**
	 * Seguindo o padrao definido pelo EPANET, o primeiro token eh a descricao
	 * do elemento e o terceiro token eh a velocidade 
	 */
	private VelocidadeNode parseVelocidade(String source) {
		
		StringTokenizer tokenizer = new StringTokenizer(source);

		String node = tokenizer.nextToken();

		tokenizer.nextToken();//ignored
		String pressure = tokenizer.nextToken();
		
		return new VelocidadeNode(Measure.valueOf(Double.parseDouble(pressure), Velocity.SI_UNIT), node);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#pressaoMinimaNode()
	 */
	public PressaoNode pressaoMinimaNode() {
		
		if(pressaoMatcher == null) throw new IllegalStateException("A IMatcher should be assigned for" +
		"use the pressaoMinimaNode method. See the Builder methods");
		
		List<PressaoNode> pressoes = parsePressoes(docItems.get(pressaoMatcher));
		Collections.sort(pressoes);
		return pressoes.get(0);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#pressaoMaximaNode()
	 */
	public PressaoNode pressaoMaximaNode() {
		
		if(pressaoMatcher == null) throw new IllegalStateException("A IMatcher should be assigned for" +
		"use the pressaoMaximaNode method. See the Builder methods");
		
		List<PressaoNode> pressoes = parsePressoes(docItems.get(pressaoMatcher));
		Collections.sort(pressoes);
		return pressoes.get(pressoes.size() - 1);
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
		
		return new PressaoNode(Measure.valueOf(Double.parseDouble(pressure), Pressure.SI_UNIT), node);
	}

	/**
	 * Os padroes setados como <code>null</code> serao ignorados.
	 * 
	 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
	 * since 07/08/2007
	 */
	public static class Builder{
		
		private IMatcher velocidadeMatcher;
		private IMatcher pressaoMatcher;
		private String totalAlarmesPattern;
		private String pressaoNegativaAlarmPattern;
		
		/**
		 * @param pressaoMatcher
		 * @return
		 */
		public Builder setPressaoMatcher(IMatcher pressaoMatcher){
			this.pressaoMatcher = pressaoMatcher;
			return this;
		}

		/**
		 * @param velocidadeMatcher
		 * @return
		 */
		public Builder setVelocidadeMatcher(IMatcher velocidadeMatcher) {
			this.velocidadeMatcher = velocidadeMatcher;
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
			
			if (velocidadeMatcher != null) {
				linedTextBuilder.addMatcher(velocidadeMatcher);
			}
			
			if (pressaoMatcher != null) {
				linedTextBuilder.addMatcher(pressaoMatcher);
			}

			IMatcher pressaoNegativaMatcher = null;
			if (pressaoNegativaAlarmPattern != null) {
				pressaoNegativaMatcher = new RegexMatcher(pressaoNegativaAlarmPattern);
				linedTextBuilder.addMatcher(pressaoNegativaMatcher);
			}
			
			IMatcher totalAlarmsRegexMatcher = null;
			if (totalAlarmesPattern != null) {
				totalAlarmsRegexMatcher = new RegexMatcher(totalAlarmesPattern);
				linedTextBuilder.addMatcher(totalAlarmsRegexMatcher);
			}
			
			return new DefaultOutPutRelatorio(linedTextBuilder.build(), totalAlarmsRegexMatcher, pressaoNegativaMatcher, 
					pressaoMatcher, velocidadeMatcher);
		}
	}

}
