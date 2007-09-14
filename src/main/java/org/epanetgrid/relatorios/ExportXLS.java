package org.epanetgrid.relatorios;

import java.util.List;

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
	
	/**
	 * @param outPutRelatorio
	 * @return
	 */
	public HSSFWorkbook createSheet(IOutPutRelatorio outPutRelatorio, IResultRelatorio resultRelatorio) {
		
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("name");
		sheet.setDefaultColumnWidth((short) (sheet.getDefaultColumnWidth() * 2));

		HSSFRow row = sheet.createRow(1);
		
		row.createCell((short) 0).setCellValue(MALHA_LABEL);
		row.createCell((short) 1).setCellValue(DATA_LABEL);
		row.createCell((short) 2).setCellValue(CUSTO_TOTAL_LABEL);
		row.createCell((short) 3).setCellValue(ALARMES_LABEL);
		row.createCell((short) 4).setCellValue(ALARMES_PRESSAO_NEG_LABEL);
		
		
		HSSFRow row2 = sheet.createRow(2);
		
		row2.createCell((short) 0).setCellValue("Malha xpto");
		row2.createCell((short) 1).setCellValue(resultRelatorio.getDataSimulacao());
		row2.createCell((short) 2).setCellValue(resultRelatorio.getCusto().toString());
		row2.createCell((short) 3).setCellValue(outPutRelatorio.getNumTotalAlarmes());
		row2.createCell((short) 4).setCellValue(outPutRelatorio.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO));

		//Malha	Pressão mínima [m]	Elemento	Pressão máxima [m]	Elemento

		
		return wb;
	}
	
	private void exportResults(List testes, String absolutePath) {
		/**
		HSSFWorkbook wb = new HSSFWorkbook();

		for (Iterator it = testes.iterator(); it.hasNext(); ) {
			HSSFSheet sheet = wb.createSheet("name");
			sheet.setDefaultColumnWidth((short) (sheet.getDefaultColumnWidth() * 2));
			short rowCount = 0;
			HSSFRow row = sheet.createRow(rowCount++);
			for (short colCount = 0; colCount < tableModel.getColumnCount(); colCount++) {
				row.createCell(colCount).setCellValue(tableModel.getColumnName(colCount));
			}
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				row = sheet.createRow(rowCount++);
				for (short colCount = 0; colCount < tableModel.getColumnCount(); colCount++) {
					row.createCell(colCount).setCellValue((tableModel.getValueAt(i, colCount) != null ? tableModel.getValueAt(i, colCount).toString() : ""));
				}
			}
		}

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(absolutePath);
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
		*/
	}
	
}
