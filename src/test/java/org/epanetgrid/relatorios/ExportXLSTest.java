package org.epanetgrid.relatorios;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.quantities.Pressure;
import javax.quantities.Velocity;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.easymock.EasyMock;
import org.epanetgrid.relatorios.outPutRelatorios.IOutPutRelatorio;
import org.epanetgrid.relatorios.outPutRelatorios.PressaoNode;
import org.epanetgrid.relatorios.outPutRelatorios.VelocidadeNode;
import org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo;
import org.epanetgrid.relatorios.resultRelatorios.IResultRelatorio;
import org.jscience.economics.money.Money;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 14/09/2007
 */
public class ExportXLSTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.relatorios.ExportXLS.createSheet(IOutPutRelatorio)'
	 */
	public final void testCreateSheet() {
		
		
		IOutPutRelatorio report = EasyMock.createMock(IOutPutRelatorio.class);
		
		EasyMock.expect(report.getNumTotalAlarmes()).andReturn(1);
		EasyMock.expect(report.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO)).andReturn(2).anyTimes();
		
		EasyMock.expect(report.pressaoMaximaNode()).andReturn(
				new PressaoNode(Measure.valueOf(2, Pressure.SI_UNIT),"nodeX")).anyTimes();
		EasyMock.expect(report.pressaoMinimaNode()).andReturn(
				new PressaoNode(Measure.valueOf(1, Pressure.SI_UNIT),"nodeX1")).anyTimes();
		
		EasyMock.expect(report.velocidadeMaximaNode()).andReturn(
				new VelocidadeNode(Measure.valueOf(6.5, Velocity.SI_UNIT),"nodeX2")).anyTimes();
		EasyMock.expect(report.velocidadeMinimaNode()).andReturn(
				new VelocidadeNode(Measure.valueOf(5, Velocity.SI_UNIT),"nodeX3")).anyTimes();
		
		EasyMock.replay(report);
		
		
		IResultRelatorio resultReport = EasyMock.createMock(IResultRelatorio.class);
		EasyMock.expect(resultReport.getCusto()).andReturn(Measure.valueOf(1, Money.BASE_UNIT)).anyTimes();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(2006, Calendar.AUGUST, 24, 19, 17, 46);
		Date date = calendar.getTime();
		EasyMock.expect(resultReport.getDataSimulacao()).andReturn(date).anyTimes();
		
		EasyMock.replay(resultReport);
		Map<IOutPutRelatorio, IResultRelatorio> map = new HashMap<IOutPutRelatorio, IResultRelatorio>();
		map.put(report, resultReport);
		HSSFWorkbook book = new ExportXLS().createSheet(map);
		
		/** Simulacao Sheet */
		HSSFSheet sheet1 = book.getSheetAt(0);
		assertEquals(2, sheet1.getPhysicalNumberOfRows());
		
		Iterator rowIterator = sheet1.rowIterator();
		HSSFRow simulacaoRow = (HSSFRow) rowIterator.next();

		assertEquals(5, simulacaoRow.getPhysicalNumberOfCells());
		//Malha	Data da simulação	Custo total	Alarmes	Alarmes de pressão negativa
		assertEquals("Malha", simulacaoRow.getCell((short) 0).getStringCellValue());
		assertEquals("Data da simulação", simulacaoRow.getCell((short) 1).getStringCellValue());
		assertEquals("Custo total", simulacaoRow.getCell((short) 2).getStringCellValue());
		assertEquals("Alarmes", simulacaoRow.getCell((short) 3).getStringCellValue());
		assertEquals("Alarmes de pressão negativa", simulacaoRow.getCell((short) 4).getStringCellValue());
		
		simulacaoRow = (HSSFRow) rowIterator.next();

		assertEquals("Malha 1", simulacaoRow.getCell((short) 0).getStringCellValue());
		assertEquals("1.0", simulacaoRow.getCell((short) 2).getStringCellValue());
		assertEquals("1", simulacaoRow.getCell((short) 3).getStringCellValue());
		assertEquals("2", simulacaoRow.getCell((short) 4).getStringCellValue());
		
		/** Pressure Sheet */
		HSSFSheet pressureSheet = book.getSheetAt(1);
		assertEquals(2, pressureSheet.getPhysicalNumberOfRows());
		
		Iterator rowIterator2 = pressureSheet.rowIterator();
		HSSFRow pressureRow1 = (HSSFRow) rowIterator2.next(); 
		
		assertEquals(5, pressureRow1.getPhysicalNumberOfCells());
		//Malha	Pressão mínima [m]	Elemento	Pressão máxima [m]	Elemento
		assertEquals("Malha", pressureRow1.getCell((short) 0).getStringCellValue());
		assertEquals("Pressão mínima [Pa]", pressureRow1.getCell((short) 1).getStringCellValue());
		assertEquals("Elemento", pressureRow1.getCell((short) 2).getStringCellValue());
		assertEquals("Pressão máxima [Pa]", pressureRow1.getCell((short) 3).getStringCellValue());
		assertEquals("Elemento", pressureRow1.getCell((short) 4).getStringCellValue());
		
		HSSFRow pressureRow2 = (HSSFRow) rowIterator2.next();
		assertEquals(5, pressureRow2.getPhysicalNumberOfCells());
		
		assertEquals("Malha 1", pressureRow2.getCell((short) 0).getStringCellValue());
		assertEquals("1.0", pressureRow2.getCell((short) 1).getStringCellValue());
		assertEquals("nodeX1", pressureRow2.getCell((short) 2).getStringCellValue());
		assertEquals("2.0", pressureRow2.getCell((short) 3).getStringCellValue());
		assertEquals("nodeX", pressureRow2.getCell((short) 4).getStringCellValue());
		
		/** Velocity sheet */
		
		HSSFSheet velocitySheet = book.getSheetAt(2);
		assertEquals(2, velocitySheet.getPhysicalNumberOfRows());
		
		rowIterator2 = velocitySheet.rowIterator();
		
		HSSFRow velocityRow1 = (HSSFRow) rowIterator2.next();
		assertEquals(5, velocityRow1.getPhysicalNumberOfCells());
		
		//malha velo_min. elem. velo_max. elem.
		assertEquals("Malha", velocityRow1.getCell((short) 0).getStringCellValue());
		assertEquals("Velocidade mínima [m/s]", velocityRow1.getCell((short) 1).getStringCellValue());
		assertEquals("Elemento", velocityRow1.getCell((short) 2).getStringCellValue());
		assertEquals("Velocidade máxima [m/s]", velocityRow1.getCell((short) 3).getStringCellValue());
		assertEquals("Elemento", velocityRow1.getCell((short) 4).getStringCellValue());
		
		HSSFRow velocityRow2 = (HSSFRow) rowIterator2.next();
		assertEquals(5, velocityRow2.getPhysicalNumberOfCells());
		
		assertEquals("Malha 1", velocityRow2.getCell((short) 0).getStringCellValue());
		assertEquals("5.0", velocityRow2.getCell((short) 1).getStringCellValue());
		assertEquals("nodeX3", velocityRow2.getCell((short) 2).getStringCellValue());
		assertEquals("6.5", velocityRow2.getCell((short) 3).getStringCellValue());
		assertEquals("nodeX2", velocityRow2.getCell((short) 4).getStringCellValue());
		
		new ExportXLS().exportResults(book, "specs"+File.separator+"xpto.xls");
	}

}
