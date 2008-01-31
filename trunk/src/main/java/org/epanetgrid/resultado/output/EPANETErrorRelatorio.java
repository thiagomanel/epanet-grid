/**
 * 
 */
package org.epanetgrid.resultado.output;

import java.io.File;
import java.io.FileNotFoundException;

import org.epanetgrid.relatorios.common.LinedTextFileDoc;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * @author Vinicius Ferraz since 18/07/2007
 */
public class EPANETErrorRelatorio {

	/**
	 * @param output
	 * @return
	 * @throws FileNotFoundException
	 */
	public DefaultErrorRelatorio generateOutPutRelatorio(File output) throws FileNotFoundException{
		return new DefaultErrorRelatorio.Builder()
						.setWarningAlarmesPattern(".*WARNING.*")
						.setInputAlarmesPattern(".*Input Error.*")
						.setDataSimulacaoPattern(".*Page 1.*")
						.build(new LinedTextFileDoc.Builder(output));
	}
}
