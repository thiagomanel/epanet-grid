package org.epanetgrid.relatorios.resultRelatorios;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
import org.jscience.economics.money.Money;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 18/07/2007
 */
public class DefaultResultRelatorio implements IResultRelatorio{

	private final LinedTextFileDoc linedText;
	private Measure<Money> money;
	private Date data;
	
	/**
	 * @param custoSimulacaoMatcher 
	 * @param dataSimulacaoMatcher 
	 * @param linedTextBuilder 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private DefaultResultRelatorio(IMatcher dataSimulacaoMatcher, IMatcher custoSimulacaoMatcher, LinedTextFileDoc.Builder
			linedTextBuilder) throws IOException, ParseException {
		
		if(dataSimulacaoMatcher != null) linedTextBuilder.addMatcher(dataSimulacaoMatcher);
		if(custoSimulacaoMatcher != null) linedTextBuilder.addMatcher(custoSimulacaoMatcher);
		
		this.linedText = linedTextBuilder.build(); 
		
		this.money = (custoSimulacaoMatcher != null) ? parseMoneyString(linedText.getDocItems().get(custoSimulacaoMatcher)
					.iterator().next().getSource()) : null;
		
		this.data = (dataSimulacaoMatcher != null) ? parseDate(linedText.getDocItems().get(dataSimulacaoMatcher)
					.iterator().next().getSource()) : null;
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
	public Date getDataSimulacao() {
		return data;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.IResultRelatorio#getCusto()
	 */
	public Measure<Money> getCusto() {
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
	
	public static class Builder {

		private String dataSimulacaoPattern;
		private String custoSimulacaoPattern;

		/**
		 * @param dataSimulacaoPattern
		 * @return
		 */
		public Builder setDataSimulacaoPattern(String dataSimulacaoPattern) {
			this.dataSimulacaoPattern = dataSimulacaoPattern;
			return this;
		}

		public DefaultResultRelatorio build(LinedTextFileDoc.Builder linedTextBuilder) throws IOException, ParseException {

			if(dataSimulacaoPattern != null || custoSimulacaoPattern != null) {
				
				if(linedTextBuilder == null) throw new IllegalArgumentException("The linedTextBuilder must not be null");
				
				IMatcher dataSimulacaoMatcher = (dataSimulacaoPattern != null) ? new RegexMatcher(dataSimulacaoPattern) 
						: null;
				
				IMatcher custoSimulacaoMatcher = (custoSimulacaoPattern != null) ? new RegexMatcher(custoSimulacaoPattern) 
						: null;
				
				return new DefaultResultRelatorio(dataSimulacaoMatcher, custoSimulacaoMatcher, linedTextBuilder);
			}
			
			throw new IllegalStateException("Nenhum padrao foi atribuido");
		}

		public Builder setCustoSimulacaoPattern(String custoSimulacaoPattern) {
			this.custoSimulacaoPattern = custoSimulacaoPattern;
			return this;
		}

	}
}
