/**
 * 
 */
package org.epanetgrid.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.quantities.Dimensionless;
import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.INode;
import org.epanetgrid.model.NetworkComponent;
import org.epanetgrid.model.epanetNetWork.DefaultNetWork;
import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.DefaultPipe;
import org.epanetgrid.model.link.DefaultPump;
import org.epanetgrid.model.link.DefaultValve;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.DefaultJuntion;
import org.epanetgrid.model.nodes.DefaultReservoir;
import org.epanetgrid.model.nodes.DefaultTank;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;
import org.epanetgrid.model.nodes.DefaultJuntion.Builder;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 */
class EpaFileReader {

	/**
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> read(String filePath) throws IOException {

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

//	public static void main(String args[]) throws IOException {

//	for (Object junction : new EpaFileReader().read(args[0]).getJunctions()) {

//	System.out.println("junc "+((IJunction<?>)junction).label()+" elevation "+((IJunction<?>)junction).getElevation()+" demand "+((IJunction<?>)junction).getBaseDemandFlow());
//	};

//	}

	private interface Parser {

		public static final String COMMENTS_ID = ";";
		public static final String TYPE_DESCRIPT_ID = "[";

		public void parse(String line);

		public void collectResult(DefaultNetWork netWork);
	}

	private class BaseParser implements Parser {

		private static final String JUNCTION_ID = "JUNCTIONS";
		private static final String VALVE_ID = "VALVES";
		private static final String PIPE_ID = "PIPES";
		private static final String PUMP_ID = "PUMPS";
		private static final String RESERVOIR_ID = "RESERVOIRS";
		private static final String TANK_ID = "TANK";
		private static final String PATTERN_ID = "PATTERN";
		private static final String TIME_ID = "TIMES";
		private static final String OPTION_ID = "OPTIONS";
		private static final String REPORT_ID = "REPORT";
		private static final String ENERGY_ID = "ENERGY";
		private static final String CURVES_ID = "CURVES";
		private static final String CONTROLS_ID = "CONTROLS";
		private static final String TITLE_ID = "TITLE";
		private static final String COORDINATES_ID = "COORDINATES";
		private static final String EMITTERS_ID = "EMITTERS";
		private static final String QUALITY_ID = "QUALITY";
		private static final String STATUS_ID = "STATUS";
		private static final String RULES_ID = "RULES";
		private static final String DEMANDS_ID = "DEMANDS";
		private static final String REACTIONS_ID = "REACTIONS";
		private static final String SOURCES_ID = "SOURCES";
		private static final String MIXING_ID = "MIXING";
		private static final String VERTICES_ID = "VERTICES";
		private static final String BACKDROP_ID = "BACKDROP";

		private final Parser junctionParse = new JunctionParser();
		private final Parser valvesParser = new ValvesParser();
		private final Parser reservoirsParser = new ReservoirParser();
		private final Parser tanksParser = new TankParser();
		private final Parser pipeParser = new PipeParser();
		private final Parser pumpParser = new PumpParser();
		private final Parser patternParser = new PatterdIDParser();
		private final Parser timeParser = new TimeIDParser();
		private final Parser reportParser = new ReportIDParser();
		private final Parser optionParser = new OptionIDParser();
		private final Parser energyParser = new EnergyIDParser();
		private final Parser curvesParser = new CurvesIDParser();
		private final Parser controlsParser = new ControlsIDParser();
		private final Parser titleParser = new TitleIDParser();
		private final Parser coordinatesParser = new CoordinatesIDParser();
		private final Parser emittersParser = new EmittersIDParser();
		private final Parser qualityParser = new QualityIDParser();
		private final Parser statusParser = new StatusIDParser();
		private final Parser rulesParser = new RulesIDParser();
		private final Parser demandsParser = new DemandsIDParser();
		private final Parser reactionsParser = new ReactionsIDParser();
		private final Parser sourcesParser = new SourcesIDParser();
		private final Parser mixingParser = new MixingIDParser();
		private final Parser verticesParser = new VerticesIDParser();
		private final Parser backdropParser = new BackdropIDParser();

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
			//bug
			if(line.contains(JUNCTION_ID)){
				return junctionParse;
			}else if(line.contains(VALVE_ID)){
				return valvesParser;
			}else if(line.contains(RESERVOIR_ID)){
				return reservoirsParser;
			}else if(line.contains(TANK_ID)){
				return tanksParser;
			}else if(line.contains(PIPE_ID)){
				return pipeParser;
			}else if(line.contains(PUMP_ID)){
				return pumpParser;
			}else if(line.contains(PATTERN_ID)){
				return patternParser;
			}else if(line.contains(TIME_ID)){
				return timeParser;
			}else if(line.contains(REPORT_ID)){
				return reportParser;
			}else if(line.contains(OPTION_ID)){
				return optionParser;
			}else if(line.contains(ENERGY_ID)){
				return energyParser;
			}else if(line.contains(CURVES_ID)){
				return curvesParser;
			}else if(line.contains(CONTROLS_ID)){
				return controlsParser;
			} else if(line.contains(TITLE_ID)) {
				return titleParser;
			}else if(line.contains(COORDINATES_ID)) {
				return coordinatesParser;
			}else if(line.contains(EMITTERS_ID)) {
				return emittersParser;
			}else if(line.contains(QUALITY_ID)) {
				return qualityParser;
			}else if(line.contains(STATUS_ID)) {
				return statusParser;
			}else if(line.contains(RULES_ID)) {
				return rulesParser;
			}else if(line.contains(DEMANDS_ID)) {
				return demandsParser;
			}else if(line.contains(REACTIONS_ID)) {
				return reactionsParser;
			}else if(line.contains(SOURCES_ID)) {
				return sourcesParser;
			}else if(line.contains(MIXING_ID)) {
				return mixingParser;
			}else if(line.contains(VERTICES_ID)) {
				return verticesParser;
			}else if(line.contains(BACKDROP_ID)) {
				return backdropParser;
			}else {
				return NOPParser;
			}
		}

		public void collectResult(DefaultNetWork netWork) {
			junctionParse.collectResult(netWork);
			valvesParser.collectResult(netWork);
			reservoirsParser.collectResult(netWork);
			tanksParser.collectResult(netWork);
			pipeParser.collectResult(netWork);
			pumpParser.collectResult(netWork);
			patternParser.collectResult(netWork);
			timeParser.collectResult(netWork);
			reportParser.collectResult(netWork);
			optionParser.collectResult(netWork);
			energyParser.collectResult(netWork);
			curvesParser.collectResult(netWork);
			controlsParser.collectResult(netWork);
			titleParser.collectResult(netWork);
			coordinatesParser.collectResult(netWork);
			emittersParser.collectResult(netWork);
			qualityParser.collectResult(netWork);
			statusParser.collectResult(netWork);
			rulesParser.collectResult(netWork);
			demandsParser.collectResult(netWork);
			reactionsParser.collectResult(netWork);
			sourcesParser.collectResult(netWork);
			mixingParser.collectResult(netWork);
			verticesParser.collectResult(netWork);
			backdropParser.collectResult(netWork);
		}

		private class JunctionParser extends SimpleLineParser{

			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
//				ID     Elevation    Demand     Pattern
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
					junctionBuilder.baseDemandFlow(Measure.valueOf(Double.valueOf(demand), VolumetricFlowRate.SI_UNIT));
				}

				if(pattern != null) {
					junctionBuilder.demandPatternID(pattern);
				}

				netWork.addJuncao(junctionBuilder.build());
			}
		}

		private class ReservoirParser extends SimpleLineParser{
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
//				ID     Head   
				StringTokenizer tokenizer = new StringTokenizer(command);
				//ugly!
				String reservID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String head = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

				if(reservID == null) throw new IllegalArgumentException("Os reservatórios devem ter um ID. "+command);
				org.epanetgrid.model.nodes.DefaultReservoir.Builder reservBuilder = new DefaultReservoir.Builder(reservID, netWork);
				if(head != null) {
					reservBuilder.head(Measure.valueOf(Double.valueOf(head), Length.SI_UNIT));
				}
				netWork.addReservoir(reservBuilder.build());
			}
		}

		private class ValvesParser extends SimpleLineParser{
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
//				ID 	Node1  	Node2  	Diameter	Type	Setting     	MinorLoss  
				StringTokenizer tokenizer = new StringTokenizer(command);
				//ugly!
				String valveID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String node1 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String node2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String diameter = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String type = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String setting = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String minorLoss = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

				if(valveID == null) throw new IllegalArgumentException("As válvulas devem ter um ID. "+command);
				DefaultValve.Builder valveBuilder = new DefaultValve.Builder(valveID, netWork);
				if(diameter != null) {
					valveBuilder.diameter(Measure.valueOf(Double.valueOf(diameter), Length.SI_UNIT));
				}
				if(type != null) {
					valveBuilder.type(type);
				}
				if(setting != null) {
					valveBuilder.setting(setting);
				}
				if(minorLoss != null) {
					valveBuilder.lossCoefficient(Measure.valueOf(Double.valueOf(minorLoss), Dimensionless.SI_UNIT));
				}
				if(node1 != null && node2 != null) {
					INode noMontante = (INode) netWork.getElemento(node1);
					INode noJusante = (INode) netWork.getElemento(node2);
					netWork.addValve(valveBuilder.build(), noMontante, noJusante);	
				}

			}
		}

		private class TankParser extends SimpleLineParser{

			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
//				;ID     Elev.     InitLvl     MinLvl     MaxLvl     Diam.  
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

				netWork.addTanks(tankBuilder.build());
			}
		}

		private class PipeParser extends SimpleLineParser{

			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
//				System.out.println(command);
//				;ID     Node1     Node2     Length     Diam.     Roughness	MinorLoss   	Status
				StringTokenizer tokenizer = new StringTokenizer(command);
				//ugly!
				String pipeID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String montNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String jusaNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String lenght = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String diameter = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String roughness = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String minorLoss = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String status = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

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
				
				if(minorLoss != null) {
					pipeBuilder.lossCoefficient(Measure.valueOf(Double.valueOf(minorLoss), Dimensionless.SI_UNIT));
				}
				
				if(status != null) {
					pipeBuilder.status(status);
				}

				INode<?> montanteNode = (INode<?>) netWork.getElemento(montNode);
				INode<?> jusanteNode = (INode<?>) netWork.getElemento(jusaNode);

				IPipe<?> pipe = pipeBuilder.build();

				netWork.addPipe(pipe, montanteNode, jusanteNode);
			}
		}

		private class PumpParser extends SimpleLineParser{

			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
//				;ID     Node1     Node2     Properties    
				
				StringTokenizer tokenizer = new StringTokenizer(command);
				//ugly!
				String pipeID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String montNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				String jusaNode = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

				StringBuffer propBuffer = new StringBuffer();

				while(tokenizer.hasMoreTokens()){
					propBuffer.append(tokenizer.nextToken()+" ");
				}

				String properties = propBuffer.toString();

				if(pipeID == null) throw new IllegalArgumentException("As bombas devem ter um ID. "+command);
				DefaultPump.Builder pumpBuilder = new DefaultPump.Builder(pipeID, netWork);

				if(properties != null) {
					pumpBuilder.headCurveID(properties);
				}

				INode<?> montanteNode = (INode<?>) netWork.getElemento(montNode);
				INode<?> jusanteNode = (INode<?>) netWork.getElemento(jusaNode);

				IPump<?> pump = pumpBuilder.build();

				netWork.addPump(pump, montanteNode, jusanteNode);
				
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

		private class PatterdIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addPattern(command);
			}
		}

		private class TimeIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addTime(command);

				/*
				 * [TIMES]
				 * DURATION 24 HOURS
				 * HYDRAULIC TIMESTEP 60 MINUTES
				 * PATTERN TIMESTEP 60 MINUTES
				 * START CLOKTIME 12:00 AM
				 */
				StringTokenizer tokenizer = new StringTokenizer(command);
				//ugly!
				String element = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				if ( element != null && element.equalsIgnoreCase("DURATION") ) {
					String value = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
					String unidade = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

					if (value != null) {
						long multiplicador = 60 * 60 * 1000; //horas
						if (unidade != null) {
							if(unidade.equalsIgnoreCase("MINUTES")) {
								multiplicador = 60 * 1000;
							} else if(unidade.equalsIgnoreCase("SECONDS")) {
								multiplicador = 1000;
							}
						}
						netWork.setDuration(new Duration(new Long(value) * multiplicador));					
					}

				} else if ( element != null && element.equalsIgnoreCase("HYDRAULIC") ) {
					String timestep = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
					if (timestep != null && timestep.equalsIgnoreCase("TIMESTEP")) {
						String value = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
						String unidade = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

						if (value != null) {
							long multiplicador = 60 * 60 * 1000; //horas
							if (unidade != null) {
								if(unidade.equalsIgnoreCase("MINUTES")) {
									multiplicador = 60 * 1000;
								} else if(unidade.equalsIgnoreCase("SECONDS")) {
									multiplicador = 1000;
								}
							}
							netWork.setHydraulicTimestep(new Duration(new Long(value) * multiplicador));					
						}

					}

				} else if ( element != null && element.equalsIgnoreCase("START") ) {
					
					String clocktime = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

					if (clocktime != null && clocktime.equalsIgnoreCase("CLOCKTIME")) {
						String value = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
						String unidade = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

						if (value != null) {
							StringTokenizer st = new StringTokenizer(value, ":");
							int hora = Integer.valueOf(st.nextToken());
							int minutos = Integer.valueOf(st.nextToken());
							if (unidade != null) {
								if (unidade.equalsIgnoreCase("PM")) {
									hora = hora + 12;
								} else if (hora == 12) {
									hora = 0;
								}
							}
							netWork.setStartClockTime(new DateTime(2008, 01, 01, hora, minutos, 0, 0));					
						}

					}

				}

			}
		}

		private class OptionIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addOption(command);
			}
		}

		private class ReportIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				Scanner scanner = new Scanner(command);
				String key = scanner.next();
				scanner.useDelimiter(key);
				String value = scanner.next();
				netWork.addReport(key, value);
			}
		}

		private class EnergyIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addEnergy(command);
			}
		}

		private class CurvesIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addCurve(command);
			}
		}

		private class TitleIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addTitle(command);
			}
		}

		private class CoordinatesIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addCoordinates(command);
			}
		}

		private class EmittersIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addEmitters(command);
			}
		}

		private class QualityIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addQuality(command);
			}
		}

		private class StatusIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addStatus(command);
			}
		}

		private class RulesIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addRules(command);
			}
		}

		private class DemandsIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addDemands(command);
			}
		}

		private class ReactionsIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addReaction(command);
			}
		}

		private class SourcesIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addSource(command);
			}
		}

		private class MixingIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addMixing(command);
			}
		}

		private class VerticesIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addVertice(command);
			}
		}

		private class BackdropIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {
				netWork.addBackdrop(command);
			}
		}

		private class ControlsIDParser extends SimpleLineParser {
			@Override
			protected void collectResult(String command, DefaultNetWork netWork) {

				/*
				 * [CONTROLS]
				 * LINK 9 CLOSED AT TIME 1
				 * LINK 9 OPEN AT TIME 10
				 * LINK 9 CLOSED AT TIME 20
				 */
//				;LINK	ID     STATE	AT TIME	INTERVAL    
				StringTokenizer tokenizer = new StringTokenizer(command);

				//ugly!
				String element = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
				if ( element != null && element.equalsIgnoreCase("LINK") ) {
					String linkID = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
					String state = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
					String at = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
					String time = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
					String interval = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;

					if (linkID != null && state != null && interval != null) {
						netWork.addControl(new Integer(interval), linkID, state.equalsIgnoreCase("OPEN"));					
					}

				}


			}
		}

	}
}

