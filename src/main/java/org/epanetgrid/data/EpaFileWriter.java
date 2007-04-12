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

import org.epanetgrid.model.epanetNetWork.NetWork;

/**
 * @author thiago
 *
 */
class EpaFileWriter {

	/**
	 * @param netWork
	 * @throws IOException 
	 */
	public void write(NetWork netWork, String filePath) throws IOException {
		
		File outFile = new File(filePath);
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
		createComponentWriter().write(writer);
	}
	
	private ComponentWriter createComponentWriter() {
		CompositeComponentWriter compositeWriter = new CompositeComponentWriter();
		compositeWriter.addWriter(new JuncaoWriter());
		return compositeWriter;
	}

	/** Outputters para elementos da rede */
	
	private interface ComponentWriter{
		public void write(PrintWriter writer);
	}
	
	private class CompositeComponentWriter implements ComponentWriter {

		private Collection<ComponentWriter> writers = new LinkedList<ComponentWriter>();
		
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
		}

		protected abstract void printCore(PrintWriter writer);

		protected abstract void printHeader(PrintWriter writer);
		
	}
	
	private class JuncaoWriter extends AbstractComponentWriter {

		private Collection<ComponentWriter> writers = new LinkedList<ComponentWriter>();
		private static final String HEADER = "[JUNCTIONS]\n;ID     Elevation    Demand     Pattern\n;-------------------------------------------";

		@Override
		protected void printCore(PrintWriter writer) {
			// TODO Auto-generated method stub
		}

		@Override
		protected void printHeader(PrintWriter writer) {
			writer.println(HEADER);
		}
	}
}
