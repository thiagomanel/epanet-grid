package org.epanetgrid.relatorios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.epanetgrid.relatorios.outPutRelatorios.IOutPutRelatorio;
import org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo;
import org.epanetgrid.relatorios.resultRelatorios.IResultRelatorio;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 14/09/2007
 */
public class ExportXLS {

	private static final String MALHA_LABEL = "Malha";
	
	private static final String DATA_LABEL = "Data da simulação";
	private static final String CUSTO_TOTAL_LABEL = "Custo total";
	private static final String ALARMES_LABEL = "Alarmes";
	private static final String ALARMES_PRESSAO_NEG_LABEL = "Alarmes de pressão negativa";
	
	private static final String PRESSAO_MIN_LABEL = "Pressão mínima [Pa]";
	private static final String PRESSAO_MAX_LABEL = "Pressão máxima [Pa]";
	
	private static final String VELOCIDADE_MIN_LABEL = "Velocidade mínima [m/s]";
	private static final String VELOCIDADE_MAX_LABEL = "Velocidade máxima [m/s]";
	
	private static final String ELEMENTO_LABEL = "Elemento";
	
	/**
	 * @param outPutRelatorio
	 * @return
	 */
	public HSSFWorkbook createSheet(Map<IOutPutRelatorio, IResultRelatorio>  reports) {
		
		HSSFWorkbook wb = new HSSFWorkbook();

		//Malha	Data da simulação 	Custo total	Alarmes	Alarmes de pressão negativa
		HSSFSheet sheet = wb.createSheet("Simulacao");
		sheet.setDefaultColumnWidth((short) (sheet.getDefaultColumnWidth() * 2));

		HSSFRow row = sheet.createRow(0);
		
		row.createCell((short) 0).setCellValue(MALHA_LABEL);
		row.createCell((short) 1).setCellValue(DATA_LABEL);
		row.createCell((short) 2).setCellValue(CUSTO_TOTAL_LABEL);
		row.createCell((short) 3).setCellValue(ALARMES_LABEL);
		row.createCell((short) 4).setCellValue(ALARMES_PRESSAO_NEG_LABEL);
		
		
		int count = 1;
		for (Entry<IOutPutRelatorio, IResultRelatorio> entry : reports.entrySet()) {
			
			HSSFRow rowTemp = sheet.createRow(count);
			IResultRelatorio resultRelatorio = entry.getValue();
			IOutPutRelatorio outRelatorio = entry.getKey();
			
			rowTemp.createCell((short) 0).setCellValue("Malha "+count++);
			rowTemp.createCell((short) 1).setCellValue(resultRelatorio.getDataSimulacao().toString());
			rowTemp.createCell((short) 2).setCellValue(resultRelatorio.getCusto().getEstimatedValue()+"");
			rowTemp.createCell((short) 3).setCellValue(outRelatorio.getNumTotalAlarmes()+"");
			rowTemp.createCell((short) 4).setCellValue(outRelatorio.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO)+"");
		}

		
		//Malha	Pressão mínima [m]	Elemento	Pressão máxima [m]	Elemento
		sheet = wb.createSheet("Pressao");
		sheet.setDefaultColumnWidth((short) (sheet.getDefaultColumnWidth() * 2));

		row = sheet.createRow(0);
		
		row.createCell((short) 0).setCellValue(MALHA_LABEL);
		row.createCell((short) 1).setCellValue(PRESSAO_MIN_LABEL);
		row.createCell((short) 2).setCellValue(ELEMENTO_LABEL);
		row.createCell((short) 3).setCellValue(PRESSAO_MAX_LABEL);
		row.createCell((short) 4).setCellValue(ELEMENTO_LABEL);
		
		
		count = 1;
		for (Entry<IOutPutRelatorio, IResultRelatorio> entry : reports.entrySet()) {
			
			HSSFRow row2 = sheet.createRow(count);
			IOutPutRelatorio outPutRelatorio = entry.getKey();
			
			row2 = sheet.createRow(count);
			
			row2.createCell((short) 0).setCellValue("Malha "+count++);
			row2.createCell((short) 1).setCellValue(outPutRelatorio.pressaoMinimaNode().getPressao().getEstimatedValue()+"");
			row2.createCell((short) 2).setCellValue(outPutRelatorio.pressaoMinimaNode().getNodeName());
			row2.createCell((short) 3).setCellValue(outPutRelatorio.pressaoMaximaNode().getPressao().getEstimatedValue()+"");
			row2.createCell((short) 4).setCellValue(outPutRelatorio.pressaoMaximaNode().getNodeName());
		}
		
		
		//malha velo_min. elem. velo_max. elem.
		sheet = wb.createSheet("Velocidade");
		sheet.setDefaultColumnWidth((short) (sheet.getDefaultColumnWidth() * 2));

		row = sheet.createRow(0);
		
		row.createCell((short) 0).setCellValue(MALHA_LABEL);
		row.createCell((short) 1).setCellValue(VELOCIDADE_MIN_LABEL);
		row.createCell((short) 2).setCellValue(ELEMENTO_LABEL);
		row.createCell((short) 3).setCellValue(VELOCIDADE_MAX_LABEL);
		row.createCell((short) 4).setCellValue(ELEMENTO_LABEL);
		
		
		count = 1;
		for (Entry<IOutPutRelatorio, IResultRelatorio> entry : reports.entrySet()) {
			
			HSSFRow row2 = sheet.createRow(count);
			IOutPutRelatorio outPutRelatorio = entry.getKey();
			
			row2 = sheet.createRow(count);
			
			row2.createCell((short) 0).setCellValue("Malha "+count++);
			row2.createCell((short) 1).setCellValue(outPutRelatorio.velocidadeMinimaNode().getVelocidade().getEstimatedValue()+"");
			row2.createCell((short) 2).setCellValue(outPutRelatorio.velocidadeMinimaNode().getNodeName());
			row2.createCell((short) 3).setCellValue(outPutRelatorio.velocidadeMaximaNode().getVelocidade().getEstimatedValue()+"");
			row2.createCell((short) 4).setCellValue(outPutRelatorio.velocidadeMaximaNode().getNodeName());
		}
		
		return wb;
	}
	
	public void exportResults(HSSFWorkbook book, String absolutePath) {

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(absolutePath);
			book.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
}
