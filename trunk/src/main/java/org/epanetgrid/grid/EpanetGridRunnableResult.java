/*
 * Copyright (c) 2002-2007 Universidade Federal de Campina Grande This program
 * is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.epanetgrid.grid;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 02/09/2007
 */
public class EpanetGridRunnableResult implements Serializable {

	private final String outputFileName;
	private final List<String> logMessage;
	private final List<String> errorMessage;
	private final List<String> conteudoArquivo;
	private final String relatorioName;
	private final List<String> saidaMessage;
	private final int taskNumber;
	
	/**
	 * @param outputFileName
	 * @param outPutFileContent
	 * @param logMessage
	 * @param errorMessage
	 * @param relatorioName
	 * @param saidaMessage
	 * @param taskNumber
	 */
	public EpanetGridRunnableResult(String outputFileName, List<String> outPutFileContent, List<String> logMessage, 
			List<String> errorMessage, String relatorioName, List<String> saidaMessage, int taskNumber) {
		
		
		if(outputFileName == null) throw new IllegalArgumentException("The output file name must be not null");
		if(outPutFileContent == null) throw new IllegalArgumentException("The outPutFileContent must be not null");
		if(logMessage == null) throw new IllegalArgumentException("The logMessage must be not null");
		if(errorMessage == null) throw new IllegalArgumentException("The errorMessage must be not null");
		if(relatorioName == null) throw new IllegalArgumentException("The relatorioName must be not null");
		if(saidaMessage == null) throw new IllegalArgumentException("The saidaMessage must be not null");
		
		this.outputFileName = outputFileName;
		this.conteudoArquivo = outPutFileContent;
		this.logMessage = logMessage;
		this.errorMessage = errorMessage;
		this.relatorioName = relatorioName;
		this.saidaMessage = saidaMessage;
		this.taskNumber = taskNumber;
		
	}
	
	/**
	 * @return An unmodifiable string list, standing for log output.
	 */
	public List<String> getLogMessage(){
		return logMessage;
	}
	
	/**
	 * @return An unmodifiable string list, standing for error output.
	 */
	public List<String> getErrorMessage(){
		return errorMessage;
	}

	/**
	 * @return An unmodifiable string list, standing for epanet file output.
	 */
	public List<String> getConteudoArquivo() {
		return conteudoArquivo;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public String getRelatorioName() {
		return relatorioName;
	}

	/**
	 * @return An unmodifiable string list, standing for execution output.
	 */
	public List<String> getSaidaMessage() {
		return saidaMessage;
	}

	public int getTaskNumber() {
		return taskNumber;
	}
	
}
