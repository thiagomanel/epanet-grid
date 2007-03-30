package org.epanetgrid.perturbador.valueProvider;

import org.easymock.EasyMock;

import junit.framework.TestCase;

/**
 * @author thiagoepdc
 *
 */
public class MonteCarloValueProviderTest extends TestCase {

	private MonteCarloValueProvider monteCarlo;
	
	protected void setUp() throws Exception {
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'org.smartpumping.core.avaliadorRobustez.perturbador.MonteCarloValueProvider.getValorPerturbado(double)'
	 */
	public void testGetValorPerturbado() {
		RandomSeed seed = EasyMock.createMock(RandomSeed.class);
		EasyMock.expect(seed.getSeed()).andReturn(0.83);
		EasyMock.expect(seed.getSeed()).andReturn(0.05);
		EasyMock.expect(seed.getSeed()).andReturn(0.40);
		EasyMock.expect(seed.getSeed()).andReturn(0.64);
		EasyMock.expect(seed.getSeed()).andReturn(0.30);
		EasyMock.expect(seed.getSeed()).andReturn(0.97);
		EasyMock.replay(seed);
		monteCarlo = new MonteCarloValueProvider(0.006, 0.008, 6, seed);
		
		double[] expectSeed = new double[]{0.0076, 0.00610, 0.00680, 0.00728, 0.00660, 0.00794}; 
		double[] seeds = monteCarlo.getValorPerturbado();
		
		for (int i = 0; i < seeds.length; i++) {
			assertEquals(expectSeed[i], seeds[i], 1e-4);
		}
	}
	
	public void testConstructor(){

		//extremos
		try {
			monteCarlo = new MonteCarloValueProvider(1.5, 1.4, 1, null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		try {
			monteCarlo = new MonteCarloValueProvider(1.5, 1.5, 1, null);
		} catch (Exception e) {
			fail();
		}
		
		//numero de sementes. Deve ser positivo.
		try {
			monteCarlo = new MonteCarloValueProvider(1.5, 1.4, -1, null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		try {
			monteCarlo = new MonteCarloValueProvider(1.5, 1.4, 0, null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		//faixa de variacao. [0, ...]
		try {
			monteCarlo = new MonteCarloValueProvider(1, -1, 0.0, null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		try {
			monteCarlo = new MonteCarloValueProvider(1, 0, 0.0, null);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		try {
			monteCarlo = new MonteCarloValueProvider(1, 2.0, 0.0, null);
		} catch (Exception e) {
			fail();
		}
		
	}

}
