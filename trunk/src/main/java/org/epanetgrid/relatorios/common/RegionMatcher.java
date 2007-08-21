/**
 * 
 */
package org.epanetgrid.relatorios.common;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 19/08/2007
 */
public class RegionMatcher implements IMatcher {

	private final String pattern;

	/**
	 * @param pattern
	 */
	public RegionMatcher(String pattern) {
		
		if(pattern == null) throw new IllegalArgumentException("The pattern must not be a null reference.");
		
		this.pattern = pattern;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.common.IMatcher#match(java.lang.String)
	 */
	public boolean match(String source) {
		
		if(source == null) throw new IllegalArgumentException("The source must not be a null reference.");
		
		return source.contains(pattern);
	}

}
