package org.epanetgrid.perturbador.valueProvider;

import junit.framework.TestCase;

/**
 * @author thiagoepdc
 *
 */
public class AnaliseSensibilidadeValueProviderTest extends TestCase {

	private AnaliseSensibilidadeValueProvider analiseSensibilidade;
	
	protected void setUp() throws Exception {
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testConstructor(){

		//extremos
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(1.5, 1.4, new Double(1));
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(1.5, 1.5, new Double(1));
		} catch (Exception e) {
			fail();
		}

		//faixa de variacao. [0, ...]
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(new Double(1), -1, 0.0);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(new Double(1), 0, 0.0);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(new Double(1), 2.0, 0.0);
		} catch (Exception e) {
			fail();
		}
		
		//discretizao
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(new Double(0), 2.0, 0.0);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(new Double(-1), 2.0, 0.0);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		try {
			analiseSensibilidade = new AnaliseSensibilidadeValueProvider(new Double(1), 2.0, 0.0);
		} catch (Exception e) {
			fail();
		}
	}
}
