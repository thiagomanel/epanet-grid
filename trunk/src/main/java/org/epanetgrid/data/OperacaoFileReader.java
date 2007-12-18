package org.epanetgrid.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.quantities.Length;
import javax.quantities.Pressure;
import javax.quantities.Velocity;

import org.epanetgrid.model.epanetNetWork.DefaultNetWork;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.ITank;
import org.jscience.physics.measures.Measure;

/**
 * @author alan
 *
 */
class OperacaoFileReader {
	
	/**
	 * @param filePath
	 * @param network
	 * @return
	 * @throws IOException
	 */
	public NetWork read(String filePath, DefaultNetWork network) throws IOException {
		
		File operacaoFile = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(operacaoFile));
		Parser parser = new OperacaoParser();
		String line = null;
		
		while((line = reader.readLine()) != null){
			if(!line.trim().equals("")) {
				parser.parse(line);
			}
		}
		
		parser.collectResult(network);
		
		return network;
	}

	
	private interface Parser {
		
		public static final String COMMENTS_ID = ";";
		public static final String TYPE_DESCRIPT_ID = "[";
		
		public void parse(String line);
		
		public void collectResult(DefaultNetWork netWork);
	}
	
	private class OperacaoParser implements Parser{
		
		private static final String PRESSURE_ID = "PRESSAO";
		private static final String VELOCITY_ID = "VELOCIDADE";
		private static final String LEVEL_ID = "NIVEL";
		
		private final Parser pressureParse = new PressureParser();
		private final Parser velocityParser = new VelocityParser();
		private final Parser levelParser = new LevelParser();
		
		private final Parser NOPParser = new Parser(){
			public void parse(String line) {}
			public void collectResult(DefaultNetWork netWork) {}
		};
		
		private Parser expertParse;
		
		public OperacaoParser() {
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
			if(line.contains(PRESSURE_ID)){
				return pressureParse;
			}else if(line.contains(VELOCITY_ID)){
				return velocityParser;
			}else if(line.contains(LEVEL_ID)){
				return levelParser;
			}else {
				return NOPParser;
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			pressureParse.collectResult(netWork);
			velocityParser.collectResult(netWork);
			levelParser.collectResult(netWork);
		}
	}
	
	private class PressureParser extends SimpleLineParser{

		@Override
		protected void collectResult(String command, DefaultNetWork netWork) {
//			ID     Pmax    Pmin
			StringTokenizer tokenizer = new StringTokenizer(command);
			//ugly!
			String junctID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String maxPressure = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String minPressure = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(junctID == null) throw new IllegalArgumentException("Erro na leitura do arquivo de operação: " +
					"As junções devem ter um ID. "+command);
			
			IJunction<?> junction = (IJunction<?>) netWork.getElemento(junctID);
			
			DefaultJuntion.Builder junctionBuilder = new DefaultJuntion.Builder(junctID, netWork).copy(junction);
			
			if(maxPressure != null) {
				junctionBuilder.maxPressure(Measure.valueOf(Double.valueOf(maxPressure), Pressure.SI_UNIT));
			}
			
			if(minPressure != null) {
				junctionBuilder.minPressure(Measure.valueOf(Double.valueOf(minPressure), Pressure.SI_UNIT));
			}
			
			netWork.replaceComponent(junctID, junctionBuilder.build());
		}
	}
	
	private class VelocityParser extends SimpleLineParser{
		@Override
		protected void collectResult(String command, DefaultNetWork netWork) {
//			ID     Vmax    Vmin
			StringTokenizer tokenizer = new StringTokenizer(command);
			//ugly!
			String pipeID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String maxVelocity = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String minVelocity = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(pipeID == null) throw new IllegalArgumentException("Erro na leitura do arquivo de operação: " +
					"Os dutos devem ter um ID. "+command);
			
			IPipe<?> pipe = (IPipe<?>) netWork.getElemento(pipeID);

			if(pipe == null) throw new IllegalArgumentException("Erro na leitura do arquivo de operação: " +
					"Duto " + pipeID + " não existe na malha: " + command);
			
			DefaultPipe.Builder tankBuilder = new DefaultPipe.Builder(pipeID, netWork).copy(pipe);
			
			if(maxVelocity != null) {
				tankBuilder.maxVelocity(Measure.valueOf(Double.valueOf(maxVelocity), Velocity.SI_UNIT));
			}
			
			if(minVelocity != null) {
				tankBuilder.minVelocity(Measure.valueOf(Double.valueOf(minVelocity), Velocity.SI_UNIT));
			}
			
			netWork.replaceComponent(pipeID, tankBuilder.build());
		}
	}
	
	private class LevelParser extends SimpleLineParser{

		@Override
		protected void collectResult(String command, DefaultNetWork netWork) {
//			ID     Nmax    Nmin
			StringTokenizer tokenizer = new StringTokenizer(command);

			String tankID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String maxLevel = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
			String minLevel = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

			if(tankID == null) throw new IllegalArgumentException("Erro na leitura do arquivo de operação: " +
					"Os tanques devem ter um ID. "+command);
			
			ITank<?> tank = (ITank<?>) netWork.getElemento(tankID);
			
			DefaultTank.Builder tankBuilder = new DefaultTank.Builder(tankID, netWork).copy(tank);
			
			if(maxLevel != null) {
				tankBuilder.maximumSecurityLevel(Measure.valueOf(Double.valueOf(maxLevel), Length.SI_UNIT));
			}
			
			if(minLevel != null) {
				tankBuilder.minimumSecurityLevel(Measure.valueOf(Double.valueOf(minLevel), Length.SI_UNIT));
			}
			
			netWork.replaceComponent(tankID, tankBuilder.build());
		}
	}
	
	private abstract class SimpleLineParser implements Parser{

		private final List<String> commands = new LinkedList<String>();
		
		/* (non-Javadoc)
		 * @see org.epanetgrid.data.EpaFileReader.Parser#parse(java.lang.String)
		 */
		public void parse(String line) {
			if( (!line.startsWith(COMMENTS_ID) && (!line.startsWith(TYPE_DESCRIPT_ID)))) {
				commands.add(line);
			}
		}

		/* (non-Javadoc)
		 * @see org.epanetgrid.data.EpaFileReader.Parser#collectResult(org.epanetgrid.model.epanetNetWork.DefaultNetWork)
		 */
		public void collectResult(DefaultNetWork netWork) {
			for (String command : commands) {
				collectResult(command, netWork);
			}
		}
		
		/**
		 * @param command
		 * @param netWork
		 */
		protected abstract void collectResult(String command, DefaultNetWork netWork);
	}
	
}

