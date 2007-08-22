/**
 * 
 */
package org.epanetgrid.relatorios.common;

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

	private final String pattern;
	private final String escapePattern;
	private boolean matched = false;
	
	private final static String NEW_LINE = "\n";

	/**
	 * @param pattern
	 */
	public RegionMatcher(String pattern, String escapePatter) {
		
		if(pattern == null) throw new IllegalArgumentException("The pattern must not be a null reference.");
		
		this.pattern = pattern;
		this.escapePattern = escapePatter;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.common.IMatcher#match(java.lang.String)
	 */
	public boolean match(String linedSource) {
		
		if(linedSource == null) throw new IllegalArgumentException("The source must not be a null reference.");
		
		if(linedSource.trim().equals("")){
			linedSource = NEW_LINE;
		}
		
		if(linedSource.contains(escapePattern)){//reset counter
			matched = false;
		}
		
		matched |= linedSource.contains(pattern);
		return matched;
	}

}
