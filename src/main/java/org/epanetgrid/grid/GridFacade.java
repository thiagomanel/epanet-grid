package org.epanetgrid.grid;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gridfaith.GridFaithPool;
import org.gridfaith.interfaces.GridRunnable;
import org.gridfaith.interfaces.GridService;

public class GridFacade {

	private final Set<File> netWorkFiles = new HashSet<File>();
	private GridService gridService;
	
	/**
	 * avoid extern initialization
	 */
	private GridFacade(GridService gridService) { 
		this.gridService = gridService;
	}
	

	private void addGridExecutor(GridService grid, Set<File> netWorkFiles) {

		for (File netWorkFile : netWorkFiles) {
			String nwFileName = netWorkFile.getName();
			this.gridService.addGridRunnable(new EpanetGridRunnable(nwFileName,
												nwFileName+".out", 
												nwFileName+".log"));
		}
	}

	/**
	 * @param netWorkFile
	 */
	public void addNetWorkFile(File netWorkFile){
		if(!netWorkFiles.contains(netWorkFile)) {
			netWorkFiles.add(netWorkFile);
			gridService.addResource(netWorkFile);
		}
	}
	
	/**
	 * @param netWorkFile
	 */
	public void addNetWorkFile(String netWorkFile){
		addNetWorkFile(new File(netWorkFile));
	}
	
	/**
	 * @return
	 */
	public List execute(){
		addGridExecutor(this.gridService, this.netWorkFiles);
		return gridService.executeAll();
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
			gridService.setApplicationJar(new File(basePath+File.separator+"gridfaithNovo.jar"));
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
		public Builder addLibrary(String libraryFilePath){
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

		/**
		 * @param epanetGridNetworkFile
		 * @param outPutName
		 * @param logName
		 */
		public EpanetGridRunnable(String epanetGridNetworkFile, String outPutName, String logName){
			this.epanetGridNetworkFile = epanetGridNetworkFile;
			this.outputName = outPutName;
			this.logName = logName;
		}
		
		public Object run() {

//			StringBuffer errorMsg = new StringBuffer();
//			
//			String executionCommand = mountExecutionCommand(epanetGridNetworkFile, outputName, logName);
//			ProcessBuilder pBuilder = new ProcessBuilder(executionCommand);
//			Process process = null;
//			try {
//				process = pBuilder.start();
//			} catch (IOException e) {
//				errorMsg.append(e.getMessage());
//			}
//			if(process != null) {
//				try {
//					process.waitFor();
//				} catch (InterruptedException e) {
//					errorMsg.append(e.getMessage());
//				}
//			}
//			new EpanetGridRunnableResult(new File(outputName), new File(logName), errorMsg.toString());
			return new EpanetGridRunnableResult(null, null, "vai papai");
		}

		/**
		 * @param epanetGridNetworkFile
		 * @param outputName
		 * @param logName
		 * @return
		 */
		private String mountExecutionCommand(String epanetGridNetworkFile, String outputName, String logName) {
//			return "java -Djava.library.path=lib/linux -cp classes org.ourgrid.epanetgrid.JEpanetToolkitMain "
//								+ epanetGridNetworkFile+ " "+outputName+ " "+logName;
			return "echo manel";
		}
		
	}
	
	private class EpanetGridRunnableResult {
		
		private final File outputFile;
		private final File logFile;
		private final String erroMesasage;

		/**
		 * @param outputFile
		 * @param logFile
		 * @param erroMesasage
		 */
		public EpanetGridRunnableResult(File outputFile, File logFile, String erroMesasage) {
			this.outputFile = outputFile;
			this.logFile = logFile;
			this.erroMesasage = erroMesasage;
		}
		
		public File getOutPutFile(){
			return outputFile;
		}
		
		public File getLogFile(){
			return logFile;
		}
		
		public String getErroMessage(){
			return erroMesasage;
		}
		
	}
	
}
