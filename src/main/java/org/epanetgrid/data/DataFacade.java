/**
 * 
 */
package org.epanetgrid.data;

import java.io.FileNotFoundException;

import org.epanetgrid.model.epanetNetWork.NetWork;

/**
 * Facade de manipulação de inputs-outputs.
 * Os arquivos manipulados seguem o padrão *inp do EPANET;
 * 
 * @author thiago
 */
public class DataFacade {
	
	public NetWork createNetWorkFromFile(String filePath) throws FileNotFoundException{
		return new EpaFileReader().read(filePath);
	}
	
	public void saveNetWork(NetWork netWork){
		new EpaFileWriter().write(netWork);
	}
	
}
