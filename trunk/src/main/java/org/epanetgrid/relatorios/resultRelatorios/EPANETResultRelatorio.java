/**
 * 
 */
package org.epanetgrid.relatorios.resultRelatorios;

import java.io.File;


/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * @author Vinicius Ferraz
 * since 18/07/2007
 */
public interface EPANETResultRelatorio {

	/**
	 * @param relatorio
	 * @return
	 */
	public IResultRelatorio generateRelatorio(File relatorio); 
	
}
