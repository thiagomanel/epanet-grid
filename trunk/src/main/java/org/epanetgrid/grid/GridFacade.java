package org.epanetgrid.grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.gridfaith.GridFaithPool;
import org.gridfaith.interfaces.GridRunnable;
import org.gridfaith.interfaces.GridService;

public class GridFacade {

	private final Map<File, String> netWorkFiles = new HashMap<File, String>();
	private GridService gridService;
	
	/**
	 * avoid extern initialization
	 */
	private GridFacade(GridService gridService) { 
		this.gridService = gridService;
	}
	

	private void addGridExecutor(GridService grid, Map<File, String> netWorkFiles) {

		for (File file : netWorkFiles.keySet()) {
			String nwFileName = file.getName();
			this.gridService.addGridRunnable(new EpanetGridRunnable(
												file.getName(),												
												nwFileName+".out", 
												nwFileName+".log",
												netWorkFiles.get(file)));
		}
	}

	/**
	 * @param netWorkFile
	 */
	public void addNetWorkFile(File netWorkFile, String nomeRelatorio){
		if(!netWorkFiles.containsKey(netWorkFile)) {
			netWorkFiles.put(netWorkFile, nomeRelatorio);
			gridService.addResource(netWorkFile);
		}
	}
	
	/**
	 * @param netWorkFile
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
			gridService.setRequirements("name == kinguio.lsd.ufcg.edu.br");
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
		private final String relatorioName;

		
		/**
		 * @param epanetGridNetworkFile
		 * @param outPutName
		 * @param logName
		 * @param relatorioName
		 */
		public EpanetGridRunnable(String epanetGridNetworkFile, String outPutName, String logName, String relatorioName){
			this.epanetGridNetworkFile = epanetGridNetworkFile;
			this.outputName = outPutName;
			this.logName = logName;
			this.relatorioName = relatorioName;
		}
		
		public Object run() {
			StringBuffer errorMsg = new StringBuffer();
			
			
			ProcessBuilder tarBuilder = new ProcessBuilder(tarCommand());
			
			Process execProcess = null;
			Process tarProcess = null;
			try {
				tarProcess = tarBuilder.start();
				tarProcess.waitFor();
				String[] executionCommand = javaCommand(epanetGridNetworkFile, outputName, logName);
//				ProcessBuilder execBuilder = new ProcessBuilder(executionCommand);
//				execProcess = execBuilder.start();
//				execProcess.waitFor();
//				ProcessBuilder sleepBuilder = new ProcessBuilder(sleepCommand());
//				Process sleepProcess = sleepBuilder.start();
//				sleepProcess.waitFor();
			} catch (Exception e) {
				errorMsg.append(e.getMessage());
				for (StackTraceElement stack : e.getStackTrace()) {
					errorMsg.append(stack+"\n");
				}
				e.printStackTrace();
				throw new IllegalStateException(e);
			}
			
			StringBuffer saidaAndLog = new StringBuffer();
			
//			File saida = new File("saida.txt");
			
//			if(saida.exists()) {
//				BufferedReader reader = null;
//				try {
//					reader = new BufferedReader(new FileReader(saida));
//					String line;
//					while ((line = reader.readLine())!= null) {
//						saidaAndLog.append(line);
//					}
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				finally{
//					if(reader !=  null) {
//						try {
//							reader.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//			
//			File log = new File("log.txt");
//			if(log.exists()) {
//				BufferedReader reader = null;
//				try {
//					reader = new BufferedReader(new FileReader(log));
//					String line;
//					while ((line = reader.readLine())!= null) {
//						saidaAndLog.append(line);
//					}
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				finally{
//					if(reader !=  null) {
//						try {
//							reader.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
			
			File relatorio = new File(relatorioName);
			StringBuffer conteudoRelatorio = new StringBuffer();
			if(relatorio.exists()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(relatorio));
					String line;
					while ((line = reader.readLine()) != null) {
						conteudoRelatorio.append(line);
						conteudoRelatorio.append("\n");
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
			}else {
				throw new IllegalStateException("Arquivo de relatorio inexistente");
			}
			
			File error = new File("error.txt");
			if(error.exists()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(error));
					String line;
					while ((line = reader.readLine())!= null) {
						saidaAndLog.append(line);
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
			
			return new EpanetGridRunnableResult(outputName, conteudoRelatorio, new File(logName), saidaAndLog.toString());
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
//			return new String[] {"java", "-Djava.library.path=lib/linux ", "-cp", "classes ", 
//					"org.ourgrid.epanetgrid.JEpanetToolkitMain ", epanetGridNetworkFile, outputName, logName};
			try {
				Process p = Runtime.getRuntime().exec("sh executa.sh "+ epanetGridNetworkFile);
				p.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException(e);
			}
			return new String[] {"sh", "executa.sh" , epanetGridNetworkFile};
		}
		
		private String[] sleepCommand() {
			try {
				Process p = Runtime.getRuntime().exec("cp -r *.* /local/vinicius/projeto1");
				p.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new String[] {"cp", "-r", "*.*", "/local/vinicius/projeto1"};
		}
		
	}
	
	public class EpanetGridRunnableResult implements Serializable{
		
		private final String outputFile;
		private final File logFile;
		private final String erroMesasage;

		private final StringBuffer conteudoArquivo;
		
		/**
		 * @param outputFile
		 * @param logFile
		 * @param erroMesasage
		 */
		public EpanetGridRunnableResult(String outputFile, StringBuffer conteudoArquivo,File logFile, String erroMesasage) {
			this.outputFile = outputFile;
			this.conteudoArquivo = conteudoArquivo;
			this.logFile = logFile;
			this.erroMesasage = erroMesasage;
		}
		
		public String getOutPutFile(){
			return outputFile;
		}
		
		public File getLogFile(){
			return logFile;
		}
		
		public String getErroMessage(){
			return erroMesasage;
		}

		public StringBuffer getConteudoArquivo() {
			return conteudoArquivo;
		}

		public String getErroMesasage() {
			return erroMesasage;
		}

		public String getOutputFile() {
			return outputFile;
		}
		
	}
	
}
