/**
 * 
 */
package org.epanetgrid.relatorios.common;

/**
 * <code>DocItem</code> is a result from search in a document.
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public interface IDocItem {

	/**
	 * The source is a context related to a document.
	 * For example, in a document based in line granularity, the source
	 * could be a line. 
	 * @return
	 */
	public String getSource();
	
}
