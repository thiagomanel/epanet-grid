/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;

import java.io.File;


/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * @author Vinicius Ferraz
 * since 18/07/2007
 */
public interface EPANETOutPutRelatorio {

	/**
	 * @param output
	 * @return
	 */
	public IOutPutRelatorio generateOutPutRelatorio(File output);
}
