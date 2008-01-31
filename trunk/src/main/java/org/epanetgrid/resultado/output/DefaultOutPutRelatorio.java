package org.epanetgrid.resultado.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.quantities.Pressure;
import javax.quantities.Velocity;

import org.epanetgrid.relatorios.common.IDocItem;
import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
import org.epanetgrid.relatorios.outPutRelatorios.PressaoNode;
import org.epanetgrid.relatorios.outPutRelatorios.VelocidadeNode;
import org.joda.time.DateTime;
import org.jscience.economics.money.Money;
import org.jscience.physics.measures.Measure;

public class DefaultOutPutRelatorio {

	private final LinedTextFileDoc linedText;
	private final IMatcher pressaoMatcher;
	private final IMatcher velocidadeMatcher;

	private Map<IMatcher, Collection<IDocItem>> docItems; //final
	private IMatcher dataSimulacaoMatcher;
	private IMatcher custoSimulacaoMatcher;
	private DateTime data;
	private Measure<Money> money;

	/**
	 * @param linedText
	 * @param totalAlarmesMatcher
	 * @param pressaoNegativaMatcher
	 * @param velocidadeMatcher 
	 * @param custoSimulacaoMatcher 
	 * @param dataSimulacaoMatcher 
	 */
	private DefaultOutPutRelatorio(LinedTextFileDoc linedText,
										IMatcher pressaoMatcher, IMatcher velocidadeMatcher, IMatcher dataSimulacaoMatcher, IMatcher custoSimulacaoMatcher) {
		
		this.linedText = linedText;
		this.pressaoMatcher = pressaoMatcher;
		this.velocidadeMatcher = velocidadeMatcher;
		this.dataSimulacaoMatcher = dataSimulacaoMatcher;
		this.custoSimulacaoMatcher = custoSimulacaoMatcher;

		if(linedText == null) throw new IllegalArgumentException("The linedText must not be null");
		
		//fazer o parse uma vez apenas
		try {
			this.docItems = this.linedText.getDocItems();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, VelocidadeNode>> velocidadeNodes() {
		
		Map<String, VelocidadeNode> velocidades = new HashMap<String, VelocidadeNode>();
		List<Map<String, VelocidadeNode>> cenarios = new LinkedList<Map<String, VelocidadeNode>>(); 

		cenarios.add(velocidades);
		
		for (IDocItem docItem : docItems.get(velocidadeMatcher)) {
			String source = docItem.getSource();

			String node = source.substring(0, 12).trim();
			String pressure = source.substring(25, 35).trim();
			
			if (velocidades.containsKey(node)) {
				velocidades = new HashMap<String, VelocidadeNode>();
				cenarios.add(velocidades);
			}
			
			velocidades.put(node, new VelocidadeNode(Measure.valueOf(Double.parseDouble(pressure), Velocity.SI_UNIT), node));
			
		}
		
		return cenarios;
	}

	public List<Map<String, PressaoNode>> pressaoNodes() {
		
		Map<String, PressaoNode> pressoes = new HashMap<String, PressaoNode>();
		List<Map<String, PressaoNode>> cenarios = new LinkedList<Map<String, PressaoNode>>(); 

		cenarios.add(pressoes);
		
		for (IDocItem docItem : docItems.get(pressaoMatcher)) {
			String source = docItem.getSource();
			
			String node = source.substring(0, 12).trim();
			String pressure = source.substring(35, 45).trim();

			if (pressoes.containsKey(node)) {
				pressoes = new HashMap<String, PressaoNode>();
				cenarios.add(pressoes);
			}
			
			pressoes.put(node, new PressaoNode(Measure.valueOf(Double.parseDouble(pressure), Pressure.SI_UNIT), node));
			
		}
		
		return cenarios;
	}

	private Date parseDate() throws ParseException, IOException {
		
		Collection<IDocItem> poorDateDocItems = docItems.get(dataSimulacaoMatcher);
		
		if( (poorDateDocItems == null) || (poorDateDocItems.size() == 0)) {
			throw new RuntimeException("No date pattern was found in the text file.");
		}
		
		String poorDateString = poorDateDocItems.iterator().next().getSource();
		return (dataSimulacaoMatcher != null) ? parseDate(poorDateString) : null;
	}

	private Measure<Money> parseMoney() throws IOException {
		
		Collection<IDocItem> poorStringDocItems = docItems.get(custoSimulacaoMatcher);
		
		if( (poorStringDocItems == null) || (poorStringDocItems.size() == 0)) {
			throw new RuntimeException("No money pattern was found in the text file.");
		}
		
		String poorString = poorStringDocItems.iterator().next().getSource();
		return (custoSimulacaoMatcher != null) ? parseMoneyString(poorString) : null;
	}

	private Date parseDate(String poorString) throws ParseException {
		StringTokenizer tokenizer = new StringTokenizer(poorString);
		//ignore the 2 first
		tokenizer.nextToken();
		tokenizer.nextToken();
		
		String day = tokenizer.nextToken();
		String month = tokenizer.nextToken();
		String dayOfMonth = tokenizer.nextToken();
		String completeHour = tokenizer.nextToken();
		String year = tokenizer.nextToken();
		
		String mount = day+" "+month+" "+dayOfMonth+" "+completeHour+" "+year;
		return new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US).parse(mount, new ParsePosition(0));
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IResultRelatorio#getDataSimulacao()
	 */
	public DateTime getDataSimulacao() {
		
		if(data == null) {
			try {
				data = new DateTime(parseDate().getTime());
			} catch (ParseException e) {
				throw new RuntimeException("Error parsing time pattern.", e );
			} catch (IOException e) {
				throw new RuntimeException("Error parsing time pattern.", e );
			} 
		}
		
		return data;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IResultRelatorio#getCusto()
	 */
	public Measure<Money> getCusto() {
		
		if(money == null) {
			try {
				money = parseMoney();
			} catch (IOException e) {
				throw new RuntimeException("Error parsing money pattern.", e );
			} 
		}
		
		return money;
	}

	private Measure<Money> parseMoneyString(String poorString){
		
		String poorMoney = null;
		
		StringTokenizer tokenizer = new StringTokenizer(poorString);
		
		while(tokenizer.hasMoreTokens()){
			poorMoney = tokenizer.nextToken();
		}
		
		return Measure.valueOf(Double.parseDouble(poorMoney), Money.BASE_UNIT);
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
		private String custoSimulacaoPattern;
		private String dataSimulacaoPattern;
		
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

		public Builder setCustoSimulacaoPattern(String custoSimulacaoPattern) {
			this.custoSimulacaoPattern = custoSimulacaoPattern;
			return this;
		}
		
		public Builder setDataSimulacaoPattern(String dataSimulacaoPattern) {
			this.dataSimulacaoPattern = dataSimulacaoPattern;
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
			
			IMatcher dataSimulacaoMatcher = null;
			if(dataSimulacaoPattern != null) {
				dataSimulacaoMatcher = new RegexMatcher(dataSimulacaoPattern);
				linedTextBuilder.addMatcher(dataSimulacaoMatcher);
			}
			
			
			IMatcher custoSimulacaoMatcher = null;
			if (custoSimulacaoPattern != null) {
				custoSimulacaoMatcher = new RegexMatcher(custoSimulacaoPattern);
				linedTextBuilder.addMatcher(custoSimulacaoMatcher);
			}
			
			return new DefaultOutPutRelatorio(linedTextBuilder.build(), 
					pressaoMatcher, velocidadeMatcher, dataSimulacaoMatcher, custoSimulacaoMatcher);
		}
	}

}
