package org.epanetgrid.relatorios.outPutRelatorios;

import java.io.File;
import java.io.FileNotFoundException;

import org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 06/09/2007
 */
public class EPANETOutPutRelatorioTest extends TestCase {

	private static final String fs = File.separator;
	
	private static final String testDirectory = "resources"+fs+"unit";
	private static final String pressaoTestFile = testDirectory+fs+"Relatorio-1.txt"; 
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for 'org.epanetgrid.relatorios.outPutRelatorios.EPANETOutPutRelatorio.generateOutPutRelatorio(File)'
	 */
	public final void testGenerateOutPutRelatorio() throws FileNotFoundException {

		EPANETOutPutRelatorio outReport = new EPANETOutPutRelatorio();
		
		IOutPutRelatorio report = outReport.generateOutPutRelatorio(new File(pressaoTestFile));
		
		assertEquals(0, report.getNumTotalAlarmes());
		assertEquals(0, report.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO));

		//148,95 n14
		//0 m1
		assertEquals(148.95, report.pressaoMaximaNode().getPressao().getEstimatedValue());
		assertEquals("N14", report.pressaoMaximaNode().getNodeName());
		assertEquals(0.0, report.pressaoMinimaNode().getPressao().getEstimatedValue());
		assertEquals("M1", report.pressaoMinimaNode().getNodeName());
		
		//t9 t10 1.48
		//b1 b2 b3 0 
		assertEquals(1.48, report.velocidadeMaximaNode().getVelocidade().getEstimatedValue());
		assertEquals(0.0, report.velocidadeMinimaNode().getVelocidade().getEstimatedValue());
		assertEquals("B1", report.velocidadeMinimaNode().getNodeName());
		assertEquals("T9", report.velocidadeMaximaNode().getNodeName());
	}
	
	public final void testFileNotFoundTest() {
		
		EPANETOutPutRelatorio outReport = new EPANETOutPutRelatorio();
		try {
				outReport.generateOutPutRelatorio(new File("foofile"));
			fail();
		} catch (FileNotFoundException e) {	}
	}
	
	public final void testPrint() throws FileNotFoundException {
		
		EPANETOutPutRelatorio outReport = new EPANETOutPutRelatorio();
		
		IOutPutRelatorio report = outReport.generateOutPutRelatorio(new File("resources"+fs+"accept"+fs+"Relatorio-1.txt"));
		
		System.out.println(report.getNumTotalAlarmes());
		System.out.println(report.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO));

		//148,95 n14
		//0 m1
		System.out.println(report.pressaoMaximaNode().getNodeName());
		System.out.println(report.pressaoMaximaNode().getPressao());
		System.out.println(report.pressaoMinimaNode().getNodeName());
		System.out.println(report.pressaoMinimaNode().getPressao());
		System.out.println("veloc");
		//t9 t10 1.48
		//b1 b2 b3 0 
		System.out.println(report.velocidadeMaximaNode().getNodeName());
		System.out.println(report.velocidadeMaximaNode().getVelocidade());
		System.out.println(report.velocidadeMinimaNode().getNodeName());
		System.out.println(report.velocidadeMinimaNode().getVelocidade());
	}

}
