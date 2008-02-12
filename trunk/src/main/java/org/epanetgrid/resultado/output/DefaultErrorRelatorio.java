package org.epanetgrid.resultado.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.quantities.Dimensionless;
import javax.quantities.Quantity;

import org.epanetgrid.relatorios.common.IDocItem;
import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
import org.epanetgrid.resultado.output.IAlarme.Tipo;
import org.joda.time.DateTime;
import org.joda.time.TimeOfDay;

public class DefaultErrorRelatorio {

	private final LinedTextFileDoc linedText;
	private final IMatcher warningAlarmesMatcher;

	private Map<IMatcher, Collection<IDocItem>> docItems; // final
	private IMatcher dataSimulacaoMatcher;
	private IMatcher inputAlarmeMatcher;
	private DateTime data;
	private Pattern datePatternWarning;
	private boolean executionHalted;

	/**
	 * @param linedText
	 * @param warningAlarmeMatcher
	 * @param pressaoNegativaMatcher
	 * @param velocidadeMatcher
	 * @param custoSimulacaoMatcher
	 * @param dataSimulacaoMatcher
	 */
	private DefaultErrorRelatorio(LinedTextFileDoc linedText,
			IMatcher warningAlarmeMatcher, IMatcher inputAlarmeMatcher,
			IMatcher dataSimulacaoMatcher) {
		this.datePatternWarning = Pattern.compile(".* (\\d?\\d+:\\d\\d:\\d\\d) .*");
		this.linedText = linedText;
		this.warningAlarmesMatcher = warningAlarmeMatcher;
		this.inputAlarmeMatcher = inputAlarmeMatcher;
		this.dataSimulacaoMatcher = dataSimulacaoMatcher;

		if (linedText == null)
			throw new IllegalArgumentException("The linedText must not be null");

		// fazer o parse uma vez apenas
		try {
			this.docItems = this.linedText.getDocItems();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public boolean executionHalted() {
		return this.executionHalted;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.epanetgrid.relatorios.IOutPutRelatorio#getNumTotalAlarmes()
	 */
	public List<IAlarme> getAlarmes() {
		if (warningAlarmesMatcher == null)
			throw new IllegalStateException(
					"A IMatcher should be assigned for"
							+ "use the getNumTotalAlarmes method. See the Builder methods");

		Collection<IDocItem> alarmes = docItems.get(warningAlarmesMatcher);

		List<IAlarme> result = new LinkedList<IAlarme>();
		
		if (alarmes == null) {
			return result;
		}

		for (IDocItem docItem : alarmes) {

			String source = docItem.getSource();

			Matcher matcher = datePatternWarning.matcher(source);

			DateTimeInterval dateTime = null;

			if (matcher.matches()) {
				String dateString = matcher.group(1);
				String[] dateStringSplit = dateString.split(":");
				dateTime = new DateTimeInterval(Integer
						.parseInt(dateStringSplit[0]), Integer
						.parseInt(dateStringSplit[1]), Integer
						.parseInt(dateStringSplit[2]));
			} else {
				continue;
			}

			Tipo tipo;
			
			if (source.contains("System unbalanced")) {
				if (source.contains("EXECUTION HALTED")) {
					tipo = Tipo.halted;
					this.executionHalted = true;
				} else {
					tipo = Tipo.unbalanced;
				}
			} else {
				tipo = Tipo.warning;
			}

			String description = source.substring(8).trim();
			
			result.add(new AlarmeSaida(dateTime, description, Dimensionless.SI_UNIT.getDimension(), tipo));
			
		}

		return result;
	}

	public List<IInputError> getInputErrors() {
		if (inputAlarmeMatcher == null)
			throw new IllegalStateException(
					"A IMatcher should be assigned for"
							+ "use the getNumTotalAlarmes method. See the Builder methods");

		Collection<IDocItem> alarmes = docItems.get(inputAlarmeMatcher);

		List<IInputError> result = new LinkedList<IInputError>();

		if (alarmes == null) {
			return result;
		}

		for (IDocItem docItem : alarmes) {

			String source = docItem.getSource();
			Scanner sc = new Scanner(source);

			sc.next(); // Input
			sc.next(); // Error

			String code = sc.next().split(":")[0];

			StringBuilder descriptionSB = new StringBuilder();

			result.add(new IInputError(code, sc.nextLine().trim()));
		}

		return result;
	}

	private Date parseDate() throws ParseException, IOException {

		Collection<IDocItem> poorDateDocItems = docItems
				.get(dataSimulacaoMatcher);

		if ((poorDateDocItems == null) || (poorDateDocItems.size() == 0)) {
			throw new RuntimeException(
					"No date pattern was found in the text file.");
		}

		String poorDateString = poorDateDocItems.iterator().next().getSource();
		return (dataSimulacaoMatcher != null) ? parseDate(poorDateString)
				: null;
	}

	private Date parseDate(String poorString) throws ParseException {
		StringTokenizer tokenizer = new StringTokenizer(poorString);
		// ignore the 2 first
		tokenizer.nextToken();
		tokenizer.nextToken();

		String day = tokenizer.nextToken();
		String month = tokenizer.nextToken();
		String dayOfMonth = tokenizer.nextToken();
		String completeHour = tokenizer.nextToken();
		String year = tokenizer.nextToken();

		String mount = day + " " + month + " " + dayOfMonth + " "
				+ completeHour + " " + year;
		return new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US)
				.parse(mount, new ParsePosition(0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.epanetgrid.relatorios.IResultRelatorio#getDataSimulacao()
	 */
	public DateTime getDataSimulacao() {

		if (data == null) {
			try {
				data = new DateTime(parseDate().getTime());
			} catch (ParseException e) {
				throw new RuntimeException("Error parsing time pattern.", e);
			} catch (IOException e) {
				throw new RuntimeException("Error parsing time pattern.", e);
			}
		}

		return data;
	}

	/**
	 * Os padroes setados como <code>null</code> serao ignorados.
	 * 
	 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com since 07/08/2007
	 */
	public static class Builder {

		private String warningAlarmesPattern;
		private String inputAlarmesPattern;
		private String dataSimulacaoPattern;

		/**
		 * @param warningAlarmesPattern
		 * @return
		 */
		public Builder setWarningAlarmesPattern(String warningAlarmesPattern) {
			this.warningAlarmesPattern = warningAlarmesPattern;
			return this;
		}

		public Builder setInputAlarmesPattern(String inputAlarmesPattern) {
			this.inputAlarmesPattern = inputAlarmesPattern;
			return this;
		}

		public Builder setDataSimulacaoPattern(String dataSimulacaoPattern) {
			this.dataSimulacaoPattern = dataSimulacaoPattern;
			return this;
		}

		/**
		 * Constroi um <code>DefaultOutPutRelatorio</code> caso algum padrao
		 * valido tenha sido atribuido, caso contrario lanca
		 * <code>IllegalStateException</code>.
		 * 
		 * @param linedTextBuilder
		 * @return
		 * @throws FileNotFoundException
		 */
		public DefaultErrorRelatorio build(
				LinedTextFileDoc.Builder linedTextBuilder)
				throws FileNotFoundException {

			if (linedTextBuilder == null)
				throw new IllegalArgumentException(
						"The linedTextBuilder must not be null");

			IMatcher warningAlarmsRegexMatcher = null;
			if (warningAlarmesPattern != null) {
				warningAlarmsRegexMatcher = new RegexMatcher(
						warningAlarmesPattern);
				linedTextBuilder.addMatcher(warningAlarmsRegexMatcher);
			}

			IMatcher inputAlarmsRegexMatcher = null;
			if (inputAlarmesPattern != null) {
				inputAlarmsRegexMatcher = new RegexMatcher(inputAlarmesPattern);
				linedTextBuilder.addMatcher(inputAlarmsRegexMatcher);
			}

			IMatcher dataSimulacaoMatcher = null;
			if (dataSimulacaoPattern != null) {
				dataSimulacaoMatcher = new RegexMatcher(dataSimulacaoPattern);
				linedTextBuilder.addMatcher(dataSimulacaoMatcher);
			}

			return new DefaultErrorRelatorio(linedTextBuilder.build(),
					warningAlarmsRegexMatcher, inputAlarmsRegexMatcher,
					dataSimulacaoMatcher);
		}
	}

}
