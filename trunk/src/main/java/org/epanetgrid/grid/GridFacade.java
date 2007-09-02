package org.epanetgrid.grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gridfaith.GridFaithPool;
import org.gridfaith.interfaces.GridRunnable;
import org.gridfaith.interfaces.GridService;

/**
 * Grid execution facade for EPANET simulations.
 * 
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class GridFacade {

	private final Map<File, String> netWorkFiles = new HashMap<File, String>();
	private GridService gridService;
	private int cont;
	
	/**
	 * avoid extern initialization
	 */
	private GridFacade(GridService gridService) { 
		this.gridService = gridService;
	}

	/**
	 * Adds a epanet's network file and a name for its output for
	 * grid execution.
	 * @param netWorkFile
	 * @param nomeRelatorio Simulation outputName
	 */
	public void addNetWorkFile(File netWorkFile, String nomeRelatorio){
		if(!netWorkFiles.containsKey(netWorkFile)) {
			netWorkFiles.put(netWorkFile, nomeRelatorio);
			gridService.addResource(netWorkFile);
		}
	}
	
	/**
	 * Adds a epanet's network file and a name for its output for
	 * grid execution.
	 * @param netWorkFile
	 * @param nomeRelatorio Simulation outputName.
	 */
	public void addNetWorkFile(String netWorkFile, String nomeRelatorio){
		addNetWorkFile(new File(netWorkFile), nomeRelatorio);
	}
	
	/**
	 * Fires the grid execution.
	 * @return
	 */
	public List execute(){
		addGridExecutor(this.gridService, this.netWorkFiles);
		return gridService.executeAll();
	}
	
	private void addGridExecutor(GridService grid, Map<File, String> netWorkFiles) {
		for (File file : netWorkFiles.keySet()) {
			String nwFileName = file.getName();
			this.cont++;
			this.gridService.addGridRunnable(new EpanetGridRunnable(
												file.getName(),												
												nwFileName+".out", 
												nwFileName+".log",
												netWorkFiles.get(file), cont));
		}
	}
	
	//static factory
	public static class Builder{
		
		private final GridService gridService;
		private final String basePath;
		
		/**
		 * @param basePath
		 */
		public Builder(String basePath) {
			this.basePath = basePath;
			gridService = GridFaithPool.getInstance().getService(GridFaithPool.GRIDSERVICE_EXECUTOR);
			gridService.setApplicationJar(new File(basePath+File.separator+"gridfaith-1.3.jar"));
		}
		
		/**
		 * @return
		 */
		public GridFacade build(){
			return new GridFacade(gridService);
		}
		
		/**
		 * @param libraryFilePath
		 * @return
		 */
		public Builder addLibrary(String libraryFilePath) {
			gridService.addLibrary(new File(basePath+File.separator+libraryFilePath));
			return this;
		}
		
		/**
		 * @param resource
		 * @return
		 */
		public Builder addResource(File resource){
			gridService.addResource(resource);
			return this;
		}
		
		/**
		 * @param requirements
		 * @return
		 */
		public Builder setRequirements(String requirements){
			gridService.setRequirements(requirements);
			return this;
		}
	}
	
	private class EpanetGridRunnable implements GridRunnable {
		
		private final String outputName;
		private final String logName;
		private final String epanetGridNetworkFile;
		private final String relatorioName;
		private final int taskNumber;
		
		/**
		 * 
		 * @param epanetGridNetworkFile
		 * @param outPutName
		 * @param logName
		 * @param relatorioName
		 * @param taskNumber
		 */
		public EpanetGridRunnable(String epanetGridNetworkFile, String outPutName, String logName, 
				String relatorioName, int taskNumber){
			this.epanetGridNetworkFile = epanetGridNetworkFile;
			this.outputName = outPutName;
			this.logName = logName;
			this.relatorioName = relatorioName;
			this.taskNumber = taskNumber;
		}
		
		public Object run() {

			StringBuffer errorMsg = new StringBuffer();
			
			ProcessBuilder tarBuilder = new ProcessBuilder(tarCommand());
			
			Process tarProcess = null;
			try {
				tarProcess = tarBuilder.start();
				tarProcess.waitFor();
				String[] executionCommand = javaCommand(epanetGridNetworkFile, outputName, logName);
				Process p = Runtime.getRuntime().exec(executionCommand);
				p.waitFor();
			} catch (Exception e) {
				errorMsg.append(e.getMessage());
				for (StackTraceElement stack : e.getStackTrace()) {
					errorMsg.append(stack+"\n");
				}
				e.printStackTrace();
				throw new IllegalStateException(e);
			}
			
			return new EpanetGridRunnableResult(outputName, getTextContent(relatorioName), getTextContent("log.txt"),
													getTextContent("error.txt"), relatorioName, getTextContent("saida.txt"), taskNumber);
		}

		private List<String> getTextContent(String fileName) {
			
			List<String> textOutPut = new LinkedList<String>();
			File file = new File(fileName);
			if(file.exists()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String line;
					while ((line = reader.readLine())!= null) {
						textOutPut.add(line);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					if(reader !=  null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			else {
				return new LinkedList<String>();
			}
			return textOutPut;
		}

		/**
		 * @param epanetGridNetworkFile
		 * @param outputName
		 * @param logName
		 * @return
		 */
		private String[] tarCommand() {
			return new String[] {"tar", "xzvf", "epanetgrid.tar.gz"} ;
		}
		
		private String[] javaCommand(String epanetGridNetworkFile, String outputName, String logName) {
			return new String[] {"sh", "executa.sh" , epanetGridNetworkFile};
		}
	}
	
}
