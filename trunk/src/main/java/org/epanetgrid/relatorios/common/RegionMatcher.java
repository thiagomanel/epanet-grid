/**
 * 
 */
package org.epanetgrid.relatorios.common;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Esta implementacao de <code>IMatcher</code> guarda estado. Ou seja
 * a resposta de uma chamada de método depende das chamadas anteriores.
 * Certas regras de matching so sao ativadas devido ao disparo ou ausencia
 * de disparo em regras anteriores.
 * Este matcher e usado durante o parse linha por linha de um arquivo texto, por 
 * exemplo:
 * 
 * xpto-xpto-xpto-xpto
 * Important Node ---------------
 * xpto-xpto-xpto-xpto-----------
 * xpto-xpto-xpto-xpto-----------
 * xpto-xpto-xpto-xpto-----------
 * Important Node-----------
 * xpto-xpto-xpto-xpto
 * xpto-xpto-xpto-xpto
 * 
 * Este matcher pode ser usado para implementar o reconhecimento de elementos entre
 * as linhas Important Node.  
 * 
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 19/08/2007
 */
public class RegionMatcher implements IMatcher {

	private final String escapePattern;
	private final List<RecognizeSequence> sequence;
	private final static String NEW_LINE = "\n";
	private final Recognizer recognizer;
	
	/**
	 * 
	 */
	private RegionMatcher() {
		this(new LinkedList<RecognizeSequence>(), "");
	}

	/**
	 * @param sequence
	 * @param escape
	 */
	private RegionMatcher(List<RecognizeSequence> sequence, String escape) {
		this.sequence = Collections.unmodifiableList(sequence);
		this.escapePattern = escape;
		List<RecognizeSequence> stubList = new LinkedList<RecognizeSequence>();
		stubList.addAll(this.sequence);
		this.recognizer = new Recognizer(stubList);
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.common.IMatcher#match(java.lang.String)
	 */
	public boolean match(String linedSource) {
		
		if(linedSource == null) throw new IllegalArgumentException("The source must not be a null reference.");
		
		if(linedSource.trim().equals("")){
			linedSource = NEW_LINE;
		}

		return recognizer.recognize(linedSource);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((escapePattern == null) ? 0 : escapePattern.hashCode());
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj){
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final RegionMatcher other = (RegionMatcher) obj;
		if (escapePattern == null) {
			if (other.escapePattern != null) {
				return false;
			}
		} else if (!escapePattern.equals(other.escapePattern)) {
			return false;
		}
		
		if (sequence == null) {
			if (other.sequence != null) {
				return false;
			}
		} else if (!sequence.equals(other.sequence)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
	 * since 27/08/2007
	 */
	private class Recognizer {

		private final List<RecognizeSequence> sequence;
		private int patternOccurrence;
		private boolean escapePatternDetected;
		private boolean recogStart;
		private RecognizeSequence recogSequence;
		private int recogPoint;
		
		/**
		 * @param sequence
		 */
		public Recognizer(List<RecognizeSequence> sequence) {
			this.sequence = sequence;
			resetState();
		}
		
		private boolean finishOfRecogSequence() {
			return recogPoint >= (sequence.size() - 1);
		}
		
		private void resetState() {

			recogPoint = 0;
			recogStart = false;
			escapePatternDetected = false;
			
			recogSequence = sequence.get(recogPoint);
			patternOccurrence = recogSequence.occurrences;
		}

		/**
		 * @param linedSource
		 * @return
		 */
		public boolean recognize(String linedSource) {
			
			if(checkScapePattern(linedSource)) {
				return false;
			}
			
			return checkRecogPattern(linedSource);
		}

		private boolean checkRecogPattern(String linedSource) {
			
			if(recogStart) return true;
			
			if(recognizeRecogSentence(linedSource)) {//compil
				--patternOccurrence;//ugly
			}
			
			if(patternOccurrence == 0){//ugly
				if(!finishOfRecogSequence()){
					fowardPattern();
				}else {
					recogStart = true;//ugly
					escapePatternDetected = false;//ugly
				}
				
			}
			
			return false;
		}

		/**
		 * 
		 */
		private void fowardPattern() {
			recogSequence = sequence.get(++recogPoint);
			patternOccurrence = recogSequence.occurrences;
		}

		/**
		 * @param linedSource
		 * @return
		 */
		private boolean recognizeRecogSentence(String linedSource) {
			
			if (recogStart) return true;
			
			return (recogSequence != null) ? linedSource.contains(recogSequence.pattern) 
					: true; 
		}

		/**
		 * @param linedSource
		 */
		private boolean checkScapePattern(String linedSource) {//compil regex
			
			if(linedSource.contains(escapePattern)) {
				resetState();
				return true;
			}
			
			return false;
		}
	}
	
	/**
	 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
	 * since 27/08/2007
	 */
	private static class RecognizeSequence {
		
		public final String pattern;
		public int occurrences;
		
		/**
		 * @param pattern
		 * @param occurrences
		 */
		private RecognizeSequence(String pattern, int occurrences) {
			this.pattern = pattern;
			this.occurrences = occurrences;
		}
		
		public static RecognizeSequence createRecognizeSequence(String pattern, int occurrences) {
			return new RecognizeSequence(pattern, occurrences);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + occurrences;
			result = prime * result
					+ ((pattern == null) ? 0 : pattern.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			
			final RecognizeSequence other = (RecognizeSequence) obj;
			
			if (occurrences != other.occurrences) {
				return false;
			}
			
			if (pattern == null) {
				if (other.pattern != null) {
					return false;
				}
			}else if (!pattern.equals(other.pattern)) {
				return false;
			}

			return true;
		}

		@Override
		public String toString() {
			return getClass()+" pattern: "+pattern+" ocurrences: "+occurrences;
		}
	}
	
	/**
	 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
	 * since 27/08/2007
	 */
	public static class Builder {
		
		private List<RecognizeSequence> sequence = new LinkedList<RecognizeSequence>();
		private String escape;
		
		/**
		 * @param pattern
		 * @param occurrences
		 * @return
		 */
		public Builder addRecognizeSequence(String pattern, int occurrences){
			sequence.add(RecognizeSequence.createRecognizeSequence(pattern, occurrences));
			return this;
		}
		
		/**
		 * @param pattern
		 * @return
		 */
		public Builder setEscapeSequence(String pattern){
			this.escape = pattern;
			return this;
		}
		
		public RegionMatcher build(){
			return new RegionMatcher(sequence, escape);
		}
		
	}
}
