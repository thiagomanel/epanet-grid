package org.epanetgrid.resultado.output;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.epanetgrid.relatorios.outPutRelatorios.PressaoNode;
import org.epanetgrid.relatorios.outPutRelatorios.VelocidadeNode;
import org.joda.time.DateTime;
import org.jscience.economics.money.Money;
import org.jscience.physics.measures.Measure;

public class OutputTest extends TestCase {

	private static final String fs = File.separator;
	
	private static final String testDirectory = "resources"+fs+"unit";
	private static final String pressaoTestFile = testDirectory+fs+"Relatorio-1.txt";
	
	public void testEPANETOutPutRelatorio() throws Exception {

		EPANETOutPutRelatorio relatorio = new EPANETOutPutRelatorio();
		DefaultOutPutRelatorio relatorioFinal = relatorio.generateOutPutRelatorio(new File(pressaoTestFile));
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(2007, Calendar.AUGUST, 25, 17, 47, 16);
		calendar.set(Calendar.MILLISECOND, 0);
		
		DateTime expectDateTime = new DateTime(calendar.getTime().getTime());
		DateTime resultDateTime = relatorioFinal.getDataSimulacao();

		assertEquals(expectDateTime , resultDateTime);		
		assertEquals(Measure.valueOf(11003.16, Money.BASE_UNIT), relatorioFinal.getCusto());

		List<Map<String, VelocidadeNode>> velocidades = relatorioFinal.velocidadeNodes();
		List<Map<String, PressaoNode>> pressoes = relatorioFinal.pressaoNodes();
		
		assertEquals(2, velocidades.size());
		assertEquals(2, pressoes.size());
		
		// Cenario 1
		Map<String, VelocidadeNode> velCen1 = velocidades.get(0);
		Map<String, PressaoNode> preCen1 = pressoes.get(0);
		
		assertEquals(1.36, velCen1.get("T1").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.01, velCen1.get("T2").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.00, velCen1.get("T3").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.99, velCen1.get("T4").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.98, velCen1.get("T5").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.97, velCen1.get("T6").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.96, velCen1.get("T7").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.95, velCen1.get("T8").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.48, velCen1.get("T9").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.48, velCen1.get("T10").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.75, velCen1.get("T11").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.21, velCen1.get("T12").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.21, velCen1.get("T13").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.21, velCen1.get("T14").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.52, velCen1.get("T15").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.23, velCen1.get("T16").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.17, velCen1.get("T17").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.00, velCen1.get("B1").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.00, velCen1.get("B2").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.00, velCen1.get("B3").getVelocidade().getEstimatedValue(), 0.0001);		
		
		assertEquals(78.19, preCen1.get("N1").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(28.75, preCen1.get("N2").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(33.58, preCen1.get("N3").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(39.77, preCen1.get("N4").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(22.59, preCen1.get("N5").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(14.54, preCen1.get("N6").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(21.46, preCen1.get("N7").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(12.49, preCen1.get("N8").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(7.09, preCen1.get("N9").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(80.37, preCen1.get("N10").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(10.00, preCen1.get("N11").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(31.07, preCen1.get("N12").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(43.29, preCen1.get("N13").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(148.04, preCen1.get("N14").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(17.53, preCen1.get("N15").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(43.09, preCen1.get("N16").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(25.20, preCen1.get("N17").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(0.00, preCen1.get("M1").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(20.00, preCen1.get("R1").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(10.00, preCen1.get("R2").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(10.00, preCen1.get("R3").getPressao().getEstimatedValue(), 0.0001);
		
		// Cenario 2
		Map<String, VelocidadeNode> velCen2 = velocidades.get(1);
		Map<String, PressaoNode> preCen2 = pressoes.get(1);
		
		assertEquals(1.36, velCen2.get("T1").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.00, velCen2.get("T2").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.99, velCen2.get("T3").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.98, velCen2.get("T4").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.97, velCen2.get("T5").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.96, velCen2.get("T6").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.95, velCen2.get("T7").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.94, velCen2.get("T8").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.46, velCen2.get("T9").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.46, velCen2.get("T10").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.75, velCen2.get("T11").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.21, velCen2.get("T12").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.21, velCen2.get("T13").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(1.21, velCen2.get("T14").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.52, velCen2.get("T15").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.23, velCen2.get("T16").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.17, velCen2.get("T17").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.00, velCen2.get("B1").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.00, velCen2.get("B2").getVelocidade().getEstimatedValue(), 0.0001);
		assertEquals(0.00, velCen2.get("B3").getVelocidade().getEstimatedValue(), 0.0001);

		assertEquals(78.05, preCen2.get("N1").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(28.47, preCen2.get("N2").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(33.36, preCen2.get("N3").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(39.56, preCen2.get("N4").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(22.41, preCen2.get("N5").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(14.38, preCen2.get("N6").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(21.50, preCen2.get("N7").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(12.58, preCen2.get("N8").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(7.21, preCen2.get("N9").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(80.98, preCen2.get("N10").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(10.82, preCen2.get("N11").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(31.92, preCen2.get("N12").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(44.17, preCen2.get("N13").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(148.95, preCen2.get("N14").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(18.47, preCen2.get("N15").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(44.03, preCen2.get("N16").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(26.14, preCen2.get("N17").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(0.00, preCen2.get("M1").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(19.70, preCen2.get("R1").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(10.82, preCen2.get("R2").getPressao().getEstimatedValue(), 0.0001);
		assertEquals(10.94, preCen2.get("R3").getPressao().getEstimatedValue(), 0.0001);
		
	}
	
}
