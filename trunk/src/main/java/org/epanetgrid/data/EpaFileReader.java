/**
 * 
 */
package org.epanetgrid.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.epanetgrid.model.epanetNetWork.NetWork;

/**
 * @author thiago
 *
 */
class EpaFileReader {
	
	private static final String JUNCTION_ID = "JUNCTIONS";
	private static final String VALVE_ID = "VALVES";
	private static final String PIPE_ID = "PIPES";
	private static final String PUMP_ID = "PUMPS";
	private static final String RESERVOIR_ID = "RESERVOIRS";
	private static final String TANK_ID = "TANK";

	/**
	 * @param filePath
	 * @return
	 */
	public NetWork read(String filePath) throws FileNotFoundException{
		
		File inpFile = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(inpFile));
		return null;
	}

}
