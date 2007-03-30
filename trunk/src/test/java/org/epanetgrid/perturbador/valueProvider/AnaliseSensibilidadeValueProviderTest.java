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

	/*
	 * Test method for 'org.smartpumping.core.avaliadorRobustez.perturbador.MonteCarloValueProvider.getValorPerturbado(double)'
	 */
	public void testGetValorPerturbado() {

		analiseSensibilidade = new AnaliseSensibilidadeValueProvider(0.006, 0.008, new Double(0.3));
		
		double[] expectSeed = new double[]{0.0060, 0.0066, 0.0072, 0.0078, 0.0080}; 
		double[] seeds = analiseSensibilidade.getValorPerturbado();
		
		for (int i = 0; i < expectSeed.length; i++) {
			assertEquals(expectSeed[i], seeds[i], 1e-3);
		}
		
		analiseSensibilidade = new AnaliseSensibilidadeValueProvider(new Double(0.3), 0.25, -19.1900);
		double[] expectSeed2 = new double[]{-23.9875, -21.1090, -18.2305, -15.3520, -14.3925}; 
		double[] seeds2 = analiseSensibilidade.getValorPerturbado();
		
		for (int i = 0; i < expectSeed2.length; i++) {
			assertEquals(expectSeed2[i], seeds2[i], 1e-3);
		}
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
