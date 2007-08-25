/**
 * 
 */
package org.epanetgrid.relatorios.common;

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
		this.sequence = sequence;
		this.escapePattern = escape;
		this.recognizer = new Recognizer(this.sequence);
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
	
	public static class Builder {
		
		private List<RecognizeSequence> sequence = new LinkedList<RecognizeSequence>();
		private RegionMatcher stub = new RegionMatcher();
		private String escape;
		
		/**
		 * @param pattern
		 * @param occurrences
		 * @return
		 */
		public Builder addRecognizeSequence(String pattern, int occurrences){
			sequence.add(stub.new RecognizeSequence(pattern, occurrences));
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
	
	private class Recognizer {

		private final List<RecognizeSequence> sequence;
		private int patternOccurrence;
		private boolean escapePatternDetected;
		private boolean recogStart;
		private RecognizeSequence recogSequence;
		
		/**
		 * @param sequence
		 */
		public Recognizer(List<RecognizeSequence> sequence) {
			this.sequence = sequence;
			initializeRecog();
		}
		
		private void initializeRecog() {
			
			if(sequence.isEmpty()) {
				recogStart = true;
			}else {
				recogSequence = sequence.remove(0);
				patternOccurrence = recogSequence.occurrences;
			}
		}

		public boolean recognize(String linedSource) {
			
			if(escapePatternDetected) {
				return false;
			}
			
			if(recogStart){
				return true; 
			}
			
			checkEscapePattern(linedSource);
			checkRecogPattern(linedSource);
			
			return false;
		}

		private void checkRecogPattern(String linedSource) {
			
			if(escapePatternDetected) return;
			
			if(recognizeRecogSentence(linedSource)) {//compil
				--patternOccurrence;
			}
			
			if(patternOccurrence == 0){
				if(!sequence.isEmpty()){
					fowardPattern();
				}else {
					recogStart = true;
					escapePatternDetected = false;
				}
			}
		}

		/**
		 * 
		 */
		private void fowardPattern() {
			recogSequence = sequence.remove(0);
			patternOccurrence = recogSequence.occurrences;
		}

		/**
		 * @param linedSource
		 * @return
		 */
		private boolean recognizeRecogSentence(String linedSource) {
			
			if (recogStart) return true;
			
			return (this.recogSequence != null) ? linedSource.contains(this.recogSequence.pattern) 
					: true; 
		}

		/**
		 * @param linedSource
		 */
		private void checkEscapePattern(String linedSource) {
			if(linedSource.contains(escapePattern)) {
				escapePatternDetected = true;
				recogStart = false;
			}
		}
	}
	
	private class RecognizeSequence {
		
		public final String pattern;
		public int occurrences;
		
		/**
		 * @param pattern
		 * @param occurrences
		 */
		public RecognizeSequence(String pattern, int occurrences) {
			this.pattern = pattern;
			this.occurrences = occurrences;
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
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final RecognizeSequence other = (RecognizeSequence) obj;
			if (occurrences != other.occurrences)
				return false;
			if (pattern == null) {
				if (other.pattern != null)
					return false;
			} else if (!pattern.equals(other.pattern))
				return false;
			return true;
		}
		
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RegionMatcher other = (RegionMatcher) obj;
		if (escapePattern == null) {
			if (other.escapePattern != null)
				return false;
		} else if (!escapePattern.equals(other.escapePattern))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

}
