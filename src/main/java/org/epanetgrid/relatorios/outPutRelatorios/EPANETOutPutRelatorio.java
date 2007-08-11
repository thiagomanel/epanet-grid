/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;

import java.io.File;
import java.io.FileNotFoundException;

import org.epanetgrid.relatorios.common.LinedTextFileDoc;


/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * @author Vinicius Ferraz
 * since 18/07/2007
 */
public class EPANETOutPutRelatorio {

	/**
	 * @param output
	 * @return
	 * @throws FileNotFoundException 
	 */
	public IOutPutRelatorio generateOutPutRelatorio(File output) throws FileNotFoundException{
		return new DefaultOutPutRelatorio.Builder()
						.setPressaoNegativaAlarmPattern("Negative pressures")
						.setTotalAlarmesPattern("WARNING")
						.build(new LinedTextFileDoc.Builder(output));
	}
}
