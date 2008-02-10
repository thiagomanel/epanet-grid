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
		List<Map<String, AlturaNode>> alturas = relatorioFinal.alturaNodes();
		
		assertEquals(2, velocidades.size());
		assertEquals(2, pressoes.size());
		assertEquals(2, alturas.size());
		
		// Cenario 1
		Map<String, VelocidadeNode> velCen1 = velocidades.get(0);
		Map<String, PressaoNode> preCen1 = pressoes.get(0);
		Map<String, AlturaNode> altCen1 = alturas.get(0);
		
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

		assertEquals(699.94, altCen1.get("N1").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(661.75, altCen1.get("N2").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(658.58, altCen1.get("N3").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(657.77, altCen1.get("N4").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(655.59, altCen1.get("N5").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(654.54, altCen1.get("N6").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(643.46, altCen1.get("N7").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(640.49, altCen1.get("N8").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(638.09, altCen1.get("N9").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(711.37, altCen1.get("N10").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(699.00, altCen1.get("N11").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(671.07, altCen1.get("N12").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(618.29, altCen1.get("N13").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(723.04, altCen1.get("N14").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(678.53, altCen1.get("N15").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(678.09, altCen1.get("N16").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(678.20, altCen1.get("N17").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(621.75, altCen1.get("M1").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(663.00, altCen1.get("R1").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(699.00, altCen1.get("R2").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(679.00, altCen1.get("R3").getAltura().getEstimatedValue(), 0.0001);
		
		// Cenario 2
		Map<String, VelocidadeNode> velCen2 = velocidades.get(1);
		Map<String, PressaoNode> preCen2 = pressoes.get(1);
		Map<String, AlturaNode> altCen2 = alturas.get(1);
		
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
		
		assertEquals(699.80, altCen2.get("N1").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(661.47, altCen2.get("N2").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(658.36, altCen2.get("N3").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(657.56, altCen2.get("N4").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(655.41, altCen2.get("N5").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(654.38, altCen2.get("N6").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(643.50, altCen2.get("N7").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(640.58, altCen2.get("N8").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(638.21, altCen2.get("N9").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(711.98, altCen2.get("N10").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(699.82, altCen2.get("N11").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(671.92, altCen2.get("N12").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(619.17, altCen2.get("N13").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(723.95, altCen2.get("N14").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(679.47, altCen2.get("N15").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(679.03, altCen2.get("N16").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(679.14, altCen2.get("N17").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(621.75, altCen2.get("M1").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(662.70, altCen2.get("R1").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(699.82, altCen2.get("R2").getAltura().getEstimatedValue(), 0.0001);
		assertEquals(679.94, altCen2.get("R3").getAltura().getEstimatedValue(), 0.0001);
		
	}
	
}
