/**
 * 
 */
package org.epanetgrid.data;

import java.io.IOException;

import org.epanetgrid.model.epanetNetWork.NetWork;

/**
 * Facade de manipulação de inputs-outputs.
 * Os arquivos manipulados seguem o padrão *inp do EPANET;
 * 
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class DataFacade {
	
	/**
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public NetWork createNetWorkFromFile(String filePath) throws IOException{
		return new EpaFileReader().read(filePath);
	}
	
	/**
	 * @param netWork
	 * @param filePath
	 * @throws IOException
	 */
	public void saveNetWork(NetWork netWork, String filePath) throws IOException{
		new EpaFileWriter().write(netWork, filePath);
	}
	
}
