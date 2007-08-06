/**
 * 
 */
package org.epanetgrid.relatorios.common;

import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <code>IMatcher</code> implementation using regular expressions.
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public class RegexMatcher implements IMatcher{
	
	private final Pattern pattern; 

	/**
	 * @param regex
	 */
	public RegexMatcher(String regex) {
		
		if(regex == null) throw new IllegalArgumentException("Should not receive a null " +
				"reference for regex pattern");
		
		this.pattern = Pattern.compile(regex); 
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.util.IMatcher#match(java.lang.String)
	 */
	public boolean match(String source) {
		
		if(source == null) throw new IllegalArgumentException("The source string must not be null");
		
		return pattern.matcher(source).matches();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof RegexMatcher == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		
		RegexMatcher rhs = (RegexMatcher) obj;
		
		return new EqualsBuilder()
	        .append(pattern.pattern(), rhs.pattern.pattern())
	        .isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
					.append(pattern.pattern())
					.toHashCode();
	}
}
