package org.epanetgrid.resultado.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.quantities.Dimensionless;

import org.epanetgrid.resultado.output.IAlarme.Tipo;

import junit.framework.TestCase;

public class ErrorTest extends TestCase {

	private static final String fs = File.separator;
	
	private static final String testDirectory = "resources"+fs+"unit";
	private static final String errorTestFile = testDirectory+fs+"Malha_input_error-saida.txt";
	private static final String warning1TestFile = testDirectory+fs+"Malha_nao_convergencia_1-saida.txt";
	private static final String warning2TestFile = testDirectory+fs+"Malha_nao_convergencia_2-saida.txt";
	
	public void testErrors() throws FileNotFoundException {
		DefaultErrorRelatorio rel = new EPANETErrorRelatorio().generateOutPutRelatorio(new File(errorTestFile));
		List<IInputError> inputErrors = rel.getInputErrors();
		
		assertEquals(7, inputErrors.size());
		
		assertEquals("215", inputErrors.get(0).getCode());
		assertEquals("204", inputErrors.get(1).getCode());
		assertEquals("204", inputErrors.get(2).getCode());
		assertEquals("216", inputErrors.get(3).getCode());
		assertEquals("216", inputErrors.get(4).getCode());
		assertEquals("216", inputErrors.get(5).getCode());
		assertEquals("200", inputErrors.get(6).getCode());

		assertEquals("Pump 1 is a duplicate ID.", inputErrors.get(0).getDescription());
		assertEquals("Control  refers to undefined link.", inputErrors.get(1).getDescription());
		assertEquals("Control  refers to undefined link.", inputErrors.get(2).getDescription());
		assertEquals("Energy data specified for undefined Pump 1.", inputErrors.get(3).getDescription());
		assertEquals("Energy data specified for undefined Pump 1.", inputErrors.get(4).getDescription());
		assertEquals("Energy data specified for undefined Pump 1.", inputErrors.get(5).getDescription());
		assertEquals("one or more errors in input file.", inputErrors.get(6).getDescription());
		
		List<IAlarme> alarmes = rel.getAlarmes();
		
		assertEquals(0, alarmes.size());
		
	}
	
	public void testNoErrors() throws FileNotFoundException {
		DefaultErrorRelatorio rel = new EPANETErrorRelatorio().generateOutPutRelatorio(new File(warning1TestFile));
		List<IInputError> inputErrors = rel.getInputErrors();
		List<IAlarme> alarmes = rel.getAlarmes();
		
		assertEquals(0, inputErrors.size());
		assertEquals(1, alarmes.size());
		
		assertEquals(0, alarmes.get(0).getDate().getHour());
		assertEquals(0, alarmes.get(0).getDate().getMinutes());
		assertEquals(0, alarmes.get(0).getDate().getSeconds());
		
		assertEquals(Dimensionless.SI_UNIT.getDimension(), alarmes.get(0).getTipoDeAlarme());
		assertEquals(Tipo.halted, alarmes.get(0).getTipoDeRestricao());
		assertEquals("System unbalanced at 0:00:00 hrs. EXECUTION HALTED.", alarmes.get(0).getDescricao());
	}
	
	public void testNoErrorsAgain() throws FileNotFoundException {
		DefaultErrorRelatorio rel = new EPANETErrorRelatorio().generateOutPutRelatorio(new File(warning2TestFile));
		List<IInputError> inputErrors = rel.getInputErrors();
		List<IAlarme> alarmes = rel.getAlarmes();
		
		assertEquals(0, inputErrors.size());
		assertEquals(3, alarmes.size());
		
		assertEquals(0, alarmes.get(0).getDate().getHour());
		assertEquals(0, alarmes.get(0).getDate().getMinutes());
		assertEquals(0, alarmes.get(0).getDate().getSeconds());
		
		assertEquals(Dimensionless.SI_UNIT.getDimension(), alarmes.get(0).getTipoDeAlarme());
		assertEquals(Tipo.unbalanced, alarmes.get(0).getTipoDeRestricao());
		assertEquals("System unbalanced at 0:00:00 hrs.", alarmes.get(0).getDescricao());
		
		assertEquals(1, alarmes.get(1).getDate().getHour());
		assertEquals(0, alarmes.get(1).getDate().getMinutes());
		assertEquals(0, alarmes.get(1).getDate().getSeconds());
		
		assertEquals(Dimensionless.SI_UNIT.getDimension(), alarmes.get(1).getTipoDeAlarme());
		assertEquals(Tipo.unbalanced, alarmes.get(1).getTipoDeRestricao());
		assertEquals("System unbalanced at 1:00:00 hrs.", alarmes.get(1).getDescricao());
		
		assertEquals(2, alarmes.get(2).getDate().getHour());
		assertEquals(0, alarmes.get(2).getDate().getMinutes());
		assertEquals(0, alarmes.get(2).getDate().getSeconds());
		
		assertEquals(Dimensionless.SI_UNIT.getDimension(), alarmes.get(2).getTipoDeAlarme());
		assertEquals(Tipo.unbalanced, alarmes.get(2).getTipoDeRestricao());
		assertEquals("System unbalanced at 2:00:00 hrs.", alarmes.get(2).getDescricao());
		
		
	}
	
}