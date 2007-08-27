/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;

import java.io.File;
import java.io.FileNotFoundException;

import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegionMatcher;


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
						.setPressaoMatcher(new RegionMatcher.Builder().addRecognizeSequence("Node Results", 1)
												.addRecognizeSequence("-----------", 2)
												.setEscapeSequence("\n").build())
						.setVelocidadeMatcher(new RegionMatcher.Builder().addRecognizeSequence("Link Results", 1)
												.addRecognizeSequence("-----------", 2)
												.setEscapeSequence("\n").build())
						.build(new LinedTextFileDoc.Builder(output));
	}
}
