package org.epanetgrid.relatorios.common;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public interface IMatcher {

	/**
	 * Verifies if a source string matches with a pattern.
	 * @param source
	 * @return
	 */
	public boolean match(String source);
	
}
