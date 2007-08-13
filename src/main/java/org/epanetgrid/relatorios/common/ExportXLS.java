package org.epanetgrid.relatorios.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportXLS {

	private void exportResults(List testes, String absolutePath) {
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
	}
	
}
