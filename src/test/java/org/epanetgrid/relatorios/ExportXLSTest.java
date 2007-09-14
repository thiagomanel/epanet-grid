package org.epanetgrid.relatorios;

import java.util.Date;
import java.util.Iterator;

import javax.quantities.Pressure;
import javax.quantities.Velocity;

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

import junit.framework.TestCase;

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
		EasyMock.expect(report.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO)).andReturn(2);
		
		EasyMock.expect(report.pressaoMaximaNode()).andReturn(
				new PressaoNode(Measure.valueOf(1, Pressure.SI_UNIT),"nodeX"));
		EasyMock.expect(report.pressaoMinimaNode()).andReturn(
				new PressaoNode(Measure.valueOf(2, Pressure.SI_UNIT),"nodeX1"));
		
		EasyMock.expect(report.velocidadeMaximaNode()).andReturn(
				new VelocidadeNode(Measure.valueOf(5, Velocity.SI_UNIT),"nodeX2"));
		EasyMock.expect(report.velocidadeMinimaNode()).andReturn(
				new VelocidadeNode(Measure.valueOf(6.5, Velocity.SI_UNIT),"nodeX2"));
		
		EasyMock.replay(report);
		
		
		IResultRelatorio resultReport = EasyMock.createMock(IResultRelatorio.class);
		EasyMock.expect(resultReport.getCusto()).andReturn(Measure.valueOf(1, Money.BASE_UNIT)).anyTimes();
		EasyMock.expect(resultReport.getDataSimulacao()).andReturn(new Date()).anyTimes();
		
		EasyMock.replay(resultReport);
		
		HSSFWorkbook book = new ExportXLS().createSheet(report, resultReport);
		
		HSSFSheet sheet1 = book.getSheetAt(0);
		assertEquals(2, sheet1.getPhysicalNumberOfRows());
		
		Iterator rowIterator = sheet1.rowIterator();
		
		while( rowIterator.hasNext() ) {
			HSSFRow row = (HSSFRow) rowIterator.next();
			assertEquals(5, row.getPhysicalNumberOfCells());
		}
		
		HSSFSheet pressureSheet = book.getSheetAt(1);
		assertEquals(2, pressureSheet.getPhysicalNumberOfRows());
		
		Iterator rowIterator2 = pressureSheet.rowIterator();
		
		while( rowIterator2.hasNext() ) {
			HSSFRow row = (HSSFRow) rowIterator2.next();
			assertEquals(5, row.getPhysicalNumberOfCells());
		}
		
		HSSFSheet velocitySheet = book.getSheetAt(1);
		assertEquals(2, velocitySheet.getPhysicalNumberOfRows());
		
		rowIterator2 = velocitySheet.rowIterator();
		
		while( rowIterator2.hasNext() ) {
			HSSFRow row = (HSSFRow) rowIterator2.next();
			assertEquals(5, row.getPhysicalNumberOfCells());
		}
	}

}
