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

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.INode;
import org.epanetgrid.model.epanetNetWork.DefaultNetWork;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.DefaultPump;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.DefaultReservoir;
import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.model.nodes.DefaultJuntion.Builder;
import org.epanetgrid.perturbador.perturbadores.IPerturbador;
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
		private final Parser reservoirsParser = new ReservoirParser();
		private final Parser tanksParser = new TankParser();
		private final Parser pipeParser = new PipeParser();
		private final Parser pumpParser = new PumpParser();
		
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
			}else if(line.contains(this.RESERVOIR_ID)){
				return reservoirsParser;
			}else if(line.contains(this.TANK_ID)){
				return tanksParser;
			}else if(line.contains(this.PIPE_ID)){
				return pipeParser;
			}else if(line.contains(this.PUMP_ID)){
				return pumpParser;
			}
			else {
				return NOPParser;
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			junctionParse.collectResult(netWork);
			reservoirsParser.collectResult(netWork);
			tanksParser.collectResult(netWork);
			pipeParser.collectResult(netWork);
			pumpParser.collectResult(netWork);
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
				netWork.addJuncao(createJunction(command, netWork));
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
	
	private class ReservoirParser implements Parser{

		private final Set<String> commands = new HashSet<String>();
		
		public void parse(String line) {
			if( (!line.startsWith(COMMENTS_ID) && (!line.startsWith(TYPE_DESCRIPT_ID)))) {
				commands.add(line);
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			for (String command : commands) {
				netWork.addReservoir(createReservoir(command, netWork));
			}
		}
		
		private IReservoir createReservoir(String command, DefaultNetWork netWork){
			//ID     Head   
			StringTokenizer tokenizer = new StringTokenizer(command);
			//ugly!
			String reservID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String head = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(reservID == null) throw new IllegalArgumentException("Os reservatórios devem ter um ID. "+command);
			org.epanetgrid.model.nodes.DefaultReservoir.Builder reservBuilder = new DefaultReservoir.Builder(reservID, netWork);
			if(head != null) {
				reservBuilder.head(Measure.valueOf(Double.valueOf(head), Length.SI_UNIT));
			}
			return reservBuilder.build();
		}
	}
	
	private class TankParser implements Parser{

		private final Set<String> commands = new HashSet<String>();
		
		public void parse(String line) {
			if( (!line.startsWith(COMMENTS_ID) && (!line.startsWith(TYPE_DESCRIPT_ID)))) {
				commands.add(line);
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			for (String command : commands) {
				netWork.addTanks(createTank(command, netWork));
			}
		}
		
		private ITank createTank(String command, DefaultNetWork netWork){
			//;ID     Elev.     InitLvl     MinLvl     MaxLvl     Diam.  
			StringTokenizer tokenizer = new StringTokenizer(command);
			//ugly!
			String tankID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String elevation = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String initialWLevel = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String minimWaterLevel = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String maxiWaterLevel = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String diameter = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(tankID == null) throw new IllegalArgumentException("Os tanques devem ter um ID. "+command);
			DefaultTank.Builder tankBuilder = new DefaultTank.Builder(tankID, netWork);
			
			if(elevation != null) {
				tankBuilder.elevation(Measure.valueOf(Double.valueOf(elevation), Length.SI_UNIT));
			}
			
			if(initialWLevel != null) {
				tankBuilder.initialWaterLevel(Measure.valueOf(Double.valueOf(initialWLevel), Length.SI_UNIT));
			}
			
			if(minimWaterLevel != null) {
				tankBuilder.minimumWaterLevel(Measure.valueOf(Double.valueOf(minimWaterLevel), Length.SI_UNIT));
			}
			
			if(maxiWaterLevel != null) {
				tankBuilder.maximumWaterLevel(Measure.valueOf(Double.valueOf(maxiWaterLevel), Length.SI_UNIT));
			}
			
			if(diameter != null) {
				tankBuilder.nominalDiameter(Measure.valueOf(Double.valueOf(diameter), Length.SI_UNIT));
			}
			
			return tankBuilder.build();
		}
	}
	
	private class PipeParser implements Parser{

		private final Set<String> commands = new HashSet<String>();
		
		public void parse(String line) {
			if( (!line.startsWith(COMMENTS_ID) && (!line.startsWith(TYPE_DESCRIPT_ID)))) {
				commands.add(line);
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			for (String command : commands) {
				createAndAddPipe(command, netWork);
			}
		}
		
		private void createAndAddPipe(String command, DefaultNetWork netWork){
			
			//;ID     Node1     Node2     Length     Diam.     Roughness 
			StringTokenizer tokenizer = new StringTokenizer(command);
			//ugly!
			String pipeID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String montNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String jusaNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String lenght = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String diameter = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String roughness = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(pipeID == null) throw new IllegalArgumentException("Os pipes devem ter um ID. "+command);
			DefaultPipe.Builder pipeBuilder = new DefaultPipe.Builder(pipeID, netWork);
			
			if(lenght != null) {
				pipeBuilder.length(Measure.valueOf(Double.valueOf(lenght), Length.SI_UNIT));
			}
			
			if(diameter != null) {
				pipeBuilder.diameter(Measure.valueOf(Double.valueOf(diameter), Length.SI_UNIT));
			}
			
			if(roughness != null) {
				pipeBuilder.roughnessCoefficient(Measure.valueOf(Double.valueOf(roughness), Dimensionless.SI_UNIT));
			}
			
			INode montanteNode = (INode) netWork.getElemento(montNode);
			INode jusanteNode = (INode) netWork.getElemento(jusaNode);
			
			IPipe pipe = pipeBuilder.build();

			netWork.addPipe(pipe, montanteNode, jusanteNode);
		}
	}
	
	private class PumpParser implements Parser{

		private final Set<String> commands = new HashSet<String>();
		
		public void parse(String line) {
			if( (!line.startsWith(COMMENTS_ID) && (!line.startsWith(TYPE_DESCRIPT_ID)))) {
				commands.add(line);
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			for (String command : commands) {
				createAndAddPump(command, netWork);
			}
		}
		
		private void createAndAddPump(String command, DefaultNetWork netWork){
			
			//;ID     Node1     Node2     Properties    
			StringTokenizer tokenizer = new StringTokenizer(command);
			//ugly!
			String pipeID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String montNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String jusaNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String properties = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(pipeID == null) throw new IllegalArgumentException("As bombas devem ter um ID. "+command);
			DefaultPump.Builder pumpBuilder = new DefaultPump.Builder(pipeID, netWork);
			
			if(properties != null) {
				pumpBuilder.headCurveID(properties);
			}
			
			INode montanteNode = (INode) netWork.getElemento(montNode);
			INode jusanteNode = (INode) netWork.getElemento(jusaNode);
			
			IPump pump = pumpBuilder.build();

			netWork.addPump(pump, montanteNode, jusanteNode);
		}
	}
}

