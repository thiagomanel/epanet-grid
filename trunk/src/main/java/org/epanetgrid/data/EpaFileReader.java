/**
 * 
 */
package org.epanetgrid.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.epanetNetWork.DefaultNetWork;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.DefaultJuntion.Builder;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
class EpaFileReader {
	
	/**
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public NetWork read(String filePath) throws IOException{
		
		File inpFile = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(inpFile));
		Parser parser = new BaseParser();
		String line = null;
		
		while((line = reader.readLine()) != null){
			if(!line.trim().equals("")) {
				parser.parse(line);
			}
		}
		
		DefaultNetWork network = new DefaultNetWork.Builder().build();
		parser.collectResult(network);
		
		return network;
	}

	private interface Parser {
		
		public static final String COMMENTS_ID = ";";
		public static final String TYPE_DESCRIPT_ID = "[";
		
		public void parse(String line);
		
		public void collectResult(DefaultNetWork netWork);
	}
	
	private class BaseParser implements Parser{
		
		private static final String JUNCTION_ID = "JUNCTIONS";
		private static final String VALVE_ID = "VALVES";
		private static final String PIPE_ID = "PIPES";
		private static final String PUMP_ID = "PUMPS";
		private static final String RESERVOIR_ID = "RESERVOIRS";
		private static final String TANK_ID = "TANK";
		
		private final Parser junctionParse = new JunctionParser();
		
		private final Parser NOPParser = new Parser(){
			public void parse(String line) {}
			public void collectResult(DefaultNetWork netWork) {}
		};
		
		private Parser expertParse;
		
		public BaseParser() {
			expertParse = NOPParser;
		}

		public void parse(String line) {
			getExpertParser(line).parse(line);
		}
		
		private Parser getExpertParser(String line){
			expertParse = decideExpertParser(line);
			return expertParse;
		}

		private Parser decideExpertParser(String line) {
			if(line.contains("[")){
				return decideActionParser(line);
			}else {
				return expertParse;
			}
		}

		private Parser decideActionParser(String line) {
			if(line.contains(this.JUNCTION_ID)){
				return junctionParse;
			}else {
				return NOPParser;
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			junctionParse.collectResult(netWork);
		}
	}
	
	private class JunctionParser implements Parser{

		private final Set<String> commands = new HashSet<String>();
		
		public void parse(String line) {
			if( (!line.startsWith(COMMENTS_ID) && (!line.startsWith(TYPE_DESCRIPT_ID)))) {
				commands.add(line);
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			for (String command : commands) {
				IJunction junc = createJunction(command, netWork);
				netWork.addJuncao(junc);
			}
		}
		
		private IJunction createJunction(String command, DefaultNetWork netWork){

			//ID     Elevation    Demand     Pattern
			StringTokenizer tokenizer = new StringTokenizer(command);
			//ugly!
			String junctID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String elevation = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String demand = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String pattern = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(junctID == null) throw new IllegalArgumentException("As junções devem ter um ID. "+command);
			
			Builder junctionBuilder = new DefaultJuntion.Builder(junctID, netWork);
			
			if(elevation != null) {
				junctionBuilder.elevation(Measure.valueOf(Double.valueOf(elevation), Length.SI_UNIT));
			}
			
			if(demand != null) {
				junctionBuilder.baseDemandFlow(Measure.valueOf(Double.valueOf(elevation), VolumetricFlowRate.SI_UNIT));
			}
			
			if(pattern != null) {
				junctionBuilder.demandPatternID(pattern);
			}
			
			return junctionBuilder.build();
		}
	}
	
}

