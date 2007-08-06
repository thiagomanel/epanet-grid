/**
 * 
 */
package org.epanetgrid.relatorios.common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 01/08/2007
 */
public class DefaultDocItem implements IDocItem {

	private final String source;
	
	/**
	 * @param source
	 */
	public DefaultDocItem(String source) {
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see org.epanetgrid.relatorios.common.IDocItem#getSource()
	 */
	public String getSource() {
		return source;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof DefaultDocItem == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		
		DefaultDocItem rhs = (DefaultDocItem) obj;
		
		return new EqualsBuilder()
	        .append(source, rhs.source)
	        .isEquals();
	}
	
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 37)
					.append(source)
					.toHashCode();
	}

}
