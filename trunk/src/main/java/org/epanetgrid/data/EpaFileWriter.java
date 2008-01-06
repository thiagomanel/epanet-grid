/**
 * 
 */
package org.epanetgrid.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.model.link.IPipe;
import org.epanetgrid.model.link.IPump;
import org.epanetgrid.model.link.IValve;
import org.epanetgrid.model.nodes.IJunction;
import org.epanetgrid.model.nodes.IReservoir;
import org.epanetgrid.model.nodes.ITank;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
class EpaFileWriter {

	/**
	 * @param netWork
	 * @throws IOException 
	 * @throws IOException 
	 */
	public void write(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork, String filePath) throws IOException{
		
		if(netWork == null) throw new IllegalArgumentException("The networks must be not null");
		
		File outFile = new File(filePath);
		PrintWriter writer = null;
		try {
			outFile.createNewFile();
			writer = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
			createComponentWriter(netWork).write(writer);
		} catch (IOException e) {
			throw e;
		}finally{
			if(writer != null) {
				writer.close();
			}
		}
	}
	
	private ComponentWriter createComponentWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
		CompositeComponentWriter compositeWriter = new CompositeComponentWriter();
		compositeWriter.addWriter(new JuncaoWriter(netWork));
		compositeWriter.addWriter(new ReservoirsWriter(netWork));
		compositeWriter.addWriter(new TankWriter(netWork));
		compositeWriter.addWriter(new PipeWriter(netWork));
		compositeWriter.addWriter(new PumpWriter(netWork));
		
		compositeWriter.addWriter(new OptionsWriter(netWork));
		compositeWriter.addWriter(new TimeWriter(netWork));
		compositeWriter.addWriter(new ReportWriter(netWork));
		compositeWriter.addWriter(new EnergyWriter(netWork));
		compositeWriter.addWriter(new CurveWriter(netWork));
		compositeWriter.addWriter(new PatternsWriter(netWork));
		compositeWriter.addWriter(new ControlsWriter(netWork));
		return compositeWriter;
	}

	/** Outputters para elementos da rede */
	
	private interface ComponentWriter{
		public void write(PrintWriter writer);
	}
	
	private class CompositeComponentWriter implements ComponentWriter {

		private final Collection<ComponentWriter> writers = new LinkedList<ComponentWriter>();
		
		public void addWriter(ComponentWriter writer){
			writers.add(writer);
		}
		
		public void write(PrintWriter writer) {
			for (ComponentWriter cWriter : writers) {
				cWriter.write(writer);
			}
		}
	}
	
	private abstract class AbstractComponentWriter implements ComponentWriter {

		public void write(PrintWriter writer) {
			printHeader(writer);
			printCore(writer);
			writer.append("\n");
		}

		protected abstract void printCore(PrintWriter writer);

		protected abstract void printHeader(PrintWriter writer);
	}
	
	private class JuncaoWriter extends AbstractComponentWriter {
		private static final String HEADER = "[JUNCTIONS]\n;ID     Elevation    Demand     Pattern\n;-------------------------------------------";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public JuncaoWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork){
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (IJunction<?> junction : netWork.getJunctions()) {
				printJunction(junction, writer);
			}
		}
		
		private void printJunction(IJunction<?> junction, PrintWriter writer) {
			String id = junction.label();
			String elevation = (junction.getElevation() == null ) ? "" : junction.getElevation().getEstimatedValue()+"";
			String demand = (junction.getBaseDemandFlow() == null ) ? "" : junction.getBaseDemandFlow().getEstimatedValue()+"";
			String pattern = (junction.getDemandPatternID() == null ) ? "" : junction.getDemandPatternID();
			String outPut = id+"\t"+elevation+"\t"+demand+"\t"+pattern;
			writer.println(outPut);
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);
		}
	}
	
	private class ReservoirsWriter extends AbstractComponentWriter {
		private static final String HEADER = "[RESERVOIRS]\n;ID     Head\n;---------------";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public ReservoirsWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork){
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (IReservoir<?> reservoir : netWork.getReservoirs()) {
				printReservoir(reservoir, writer);
			}
		}
		
		private void printReservoir(IReservoir<?> reservoir, PrintWriter writer) {
			String id = reservoir.label();
			String headPatternID = (reservoir.getHeadPatternID() == null ) ? "" : reservoir.getHeadPatternID();
			String head = (reservoir.getHead() == null ) ? "" : reservoir.getHead().getEstimatedValue()+"";
			String outPut = id+"\t"+head+"\t"+headPatternID;
			writer.println(outPut);
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);
		}
	}
	
	private class TankWriter extends AbstractComponentWriter {
		private static final String HEADER = "[TANKS]\n;ID     Elev.     InitLvl     MinLvl     MaxLvl     Diam.\n;---------------";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public TankWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork){
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (ITank<?> reservoir : netWork.getTanks()) {
				printTank(reservoir, writer);
			}
		}
		//;ID     Elev.     InitLvl     MinLvl     MaxLvl     Diam.
		private void printTank(ITank<?> tank, PrintWriter writer) {
			String id = tank.label();
			String elevation = (tank.getElevation() == null ) ? "" : tank.getElevation().getEstimatedValue()+"";
			String initLev = (tank.getInitialWaterLevel() == null ) ? "" : tank.getInitialWaterLevel().getEstimatedValue()+"";
			String minWaterLev = (tank.getMinimumWaterLevel() == null ) ? "" : tank.getMinimumWaterLevel().getEstimatedValue()+"";
			String maxLvl = (tank.getMaximumWaterLevel() == null ) ? "" : tank.getMaximumWaterLevel().getEstimatedValue()+"";
			String diam = (tank.getNominalDiameter() == null ) ? "" : tank.getNominalDiameter().getEstimatedValue()+"";
			String outPut = id+"\t"+elevation+"\t"+initLev+"\t"+minWaterLev+"\t"+maxLvl+"\t"+diam;
			writer.println(outPut);
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);
		}
	}
	
	private class PipeWriter extends AbstractComponentWriter {
		private static final String HEADER = "[PIPES]\n;ID     Node1     Node2     Length     Diam.     Roughness.\n;---------------";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public PipeWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork){
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (IPipe<?> pipe : netWork.getPipes()) {
				printPipe(pipe, writer);
			}
		}

		//ID     Node1     Node2     Length     Diam.     Roughness 
		private void printPipe(IPipe<?> pipe, PrintWriter writer) {
			String id = pipe.label();
			String node1 = netWork.getAnterior(pipe).label();
			String node2 = netWork.getProximo(pipe).label();
			String length = (pipe.getLength() == null ) ? "" : pipe.getLength().getEstimatedValue()+"";
			String diam = (pipe.getDiameter() == null ) ? "" : pipe.getDiameter().getEstimatedValue()+"";
			String rough = (pipe.getRoughnessCoefficient() == null ) ? "" : pipe.getRoughnessCoefficient().getEstimatedValue()+"";
			String outPut = id+"\t"+node1+"\t"+node2+"\t"+length+"\t"+diam+"\t"+rough;
			writer.println(outPut);
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);
		}
	}
	
	private class PumpWriter extends AbstractComponentWriter {
		private static final String HEADER = "[PUMPS]\n;ID     Node1     Node2     Properties.\n;---------------";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public PumpWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork){
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (IPump<?> pipe : netWork.getPumps()) {
				printPipe(pipe, writer);
			}
		}

		//ID     Node1     Node2     Properties   
		private void printPipe(IPump<?> pump, PrintWriter writer) {
			String id = pump.label();
			String node1 = netWork.getAnterior(pump).label();
			String node2 = netWork.getProximo(pump).label();
			String headCurveID = (pump.getHeadCurveID()  == null ) ? "" : pump.getHeadCurveID();
			String outPut = id+"\t"+node1+"\t"+node2+"\t"+headCurveID;
			writer.println(outPut);
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);
		}
	}
	
	private class PatternsWriter extends AbstractComponentWriter{

		private static final String HEADER = "[PATTERNS]";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public PatternsWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (String pattern : netWork.getPattern()) {
				writer.println(pattern);
			}
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);		
		}
		
	}
	
	private class OptionsWriter extends AbstractComponentWriter{

		private static final String HEADER = "[OPTIONS]";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public OptionsWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (String option : netWork.getOptions()) {
				writer.println(option);
			}
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);		
		}
		
	}
	
	private class TimeWriter extends AbstractComponentWriter{

		private static final String HEADER = "[TIMES]";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public TimeWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (String time : netWork.getTimes()) {
				writer.println(time);
			}
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);		
		}
		
	}
	
	private class ReportWriter extends AbstractComponentWriter{

		private static final String HEADER = "[REPORT]";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public ReportWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			Map<String, String> reports = netWork.getReports().getValues();
			for (String key : reports.keySet()) {
				writer.println(key+" "+reports.get(key));;
			}
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);		
		}
		
	}
	
	private class EnergyWriter extends AbstractComponentWriter{

		private static final String HEADER = "[ENERGY]";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public EnergyWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (String energy : netWork.getEnergy()) {
				writer.println(energy);
			}
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);		
		}
		
	}
	
	private class CurveWriter extends AbstractComponentWriter{

		private static final String HEADER = "[CURVES]";//FIXME COLOCAR ESTAS CONSTANTES NUM PONTO UNICO
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public CurveWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			for (String curve : netWork.getCurves()) {
				writer.println(curve);
			}
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);		
		}
		
	}
	
	private class ControlsWriter extends AbstractComponentWriter{

		private static final String HEADER = "[CONTROLS]";
		private final NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork;
		
		public ControlsWriter(NetWork<IPump<?>, IPipe<?>, ITank<?>, IJunction<?>, IValve<?>, IReservoir<?>> netWork) {
			this.netWork = netWork;
		}
		
		@Override
		protected void printCore(PrintWriter writer) {
			Map<String, Map<Integer, Boolean>> controls = netWork.getControls();
			for (Map.Entry<String, Map<Integer, Boolean>> entry : controls.entrySet()) {
				for (Map.Entry<Integer, Boolean> control : entry.getValue().entrySet()) {
					StringBuffer sb = new StringBuffer("LINK ");
					sb.append(entry.getKey() + " ");
					sb.append(control.getValue() ? "OPEN" : "CLOSED");
					sb.append(" AT TIME " + control.getKey());
					writer.println(sb.toString());
				}
				
			}
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);		
		}
		
	}
}
