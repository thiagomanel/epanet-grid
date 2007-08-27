/**
 * 
 */
package org.epanetgrid.relatorios.resultRelatorios;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.epanetgrid.relatorios.common.LinedTextFileDoc;


/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * @author Vinicius Ferraz
 * since 18/07/2007
 */
public class EPANETResultRelatorio {

	/**
	 * @param relatorio
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public IResultRelatorio generateRelatorio(File relatorio) throws IOException, ParseException{
		return new DefaultResultRelatorio.Builder().
					setCustoSimulacaoPattern(".*Total Cost.*").
					setDataSimulacaoPattern(".*Page 1.*").
					build(new LinedTextFileDoc.Builder(relatorio));
	}
	
}
