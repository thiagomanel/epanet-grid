/**
 * 
 */
package org.epanetgrid.relatorios;

import java.io.File;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * @author Vinicius Ferraz
 * since 18/07/2007
 */
public interface EPANETResultRelatorio {

	public IResultRelatorio generateRelatorio(File relatorio); 
	
}
