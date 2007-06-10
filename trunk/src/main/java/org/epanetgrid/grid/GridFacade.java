package org.epanetgrid.grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gridfaith.GridFaithPool;
import org.gridfaith.interfaces.GridRunnable;
import org.gridfaith.interfaces.GridService;

/**
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
	 * 
	 * @param netWorkFile
	 * @param nomeRelatorio
	 */
	public void addNetWorkFile(File netWorkFile, String nomeRelatorio){
		if(!netWorkFiles.containsKey(netWorkFile)) {
			netWorkFiles.put(netWorkFile, nomeRelatorio);
			gridService.addResource(netWorkFile);
		}
	}
	
	/**
	 * 
	 * @param netWorkFile
	 * @param nomeRelatorio
	 */
	public void addNetWorkFile(String netWorkFile, String nomeRelatorio){
		addNetWorkFile(new File(netWorkFile), nomeRelatorio);
	}
	
	/**
	 * @return
	 */
	public List execute(){
		addGridExecutor(this.gridService, this.netWorkFiles);
		return gridService.executeAll();
	}
	
	public void setRequirements(String requirements) {
		gridService.setRequirements(requirements);
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
			System.out.println("Adicionando biblioteca: "+new File(basePath+File.separator+libraryFilePath).getAbsolutePath());
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
	
	/**
	 * @author thiago
	 */
	public class EpanetGridRunnableResult implements Serializable{
		
		private final String outputFileName;
		private final List<String> logMessage;
		private final List<String> errorMessage;
		private final List<String> conteudoArquivo;
		private final String relatorioName;
		private final List<String> saidaMessage;
		private final int taskNumber;
		
		/**
		 * 
		 * @param outputFileName
		 * @param outPutFileContent
		 * @param logMessage
		 * @param errorMessage
		 * @param relatorioName
		 */
		public EpanetGridRunnableResult(String outputFileName, List<String> outPutFileContent, List<String> logMessage, 
				List<String> errorMessage, String relatorioName, List<String> saidaMessage, int taskNumber) {
			this.outputFileName = outputFileName;
			this.conteudoArquivo = outPutFileContent;
			this.logMessage = logMessage;
			this.errorMessage = errorMessage;
			this.relatorioName = relatorioName;
			this.saidaMessage = saidaMessage;
			this.taskNumber = taskNumber;
			
		}
		
		public List<String> getLogMessage(){
			return logMessage;
		}
		
		public List<String> getErrorMessage(){
			return errorMessage;
		}

		public List<String> getConteudoArquivo() {
			return conteudoArquivo;
		}

		public String getOutputFileName() {
			return outputFileName;
		}

		public String getRelatorioName() {
			return relatorioName;
		}

		public List<String> getSaidaMessage() {
			return saidaMessage;
		}

		public int getTaskNumber() {
			return taskNumber;
		}
	}
	
}
