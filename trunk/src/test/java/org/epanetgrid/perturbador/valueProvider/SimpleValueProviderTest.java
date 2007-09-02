package org.epanetgrid.perturbador.valueProvider;

import junit.framework.TestCase;

public class SimpleValueProviderTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public final void testGetValueProviderDiscretizacaoAndSamples() {
		
		ValueProvider vp = SimpleValueProvider.getValueProviderDiscretizacaoAndSamples(600, 0.15, 10);
		double[] samples = vp.getValorPerturbado();
		
		assertEquals(10, samples.length);
		
		assertEquals(600.0, samples[0]);
		assertEquals(690.0, samples[1]);
		assertEquals(780.0, samples[2]);
		assertEquals(870.0, samples[3]);
		assertEquals(960.0, samples[4]);
		assertEquals(1050.0, samples[5]);
		assertEquals(1140.0, samples[6]);
		assertEquals(1230.0, samples[7]);
		assertEquals(1320.0, samples[8]);
		assertEquals(1410.0, samples[9]);
		
		try {
			vp = SimpleValueProvider.getValueProviderDiscretizacaoAndSamples(600, 0.15, 0);
		} catch (IllegalArgumentException e) { }
		
		try {
			vp = SimpleValueProvider.getValueProviderDiscretizacaoAndSamples(600, 0.15, -1);
		} catch (IllegalArgumentException e) { }
	}
	
	public final void testGetValueProvider() {
		
		ValueProvider vp = SimpleValueProvider.getValueProvider(500, 800, 10);
		double[] samples = vp.getValorPerturbado();
		
		assertEquals(10, samples.length);
		
		assertEquals(500.0, samples[0], 1e-3);
		assertEquals(533.333, samples[1], 1e-3);
		assertEquals(566.667, samples[2], 1e-3);
		assertEquals(600.000, samples[3], 1e-3);
		assertEquals(633.333, samples[4], 1e-3);
		assertEquals(666.6667, samples[5], 1e-3);
		assertEquals(700.0, samples[6], 1e-3);
		assertEquals(733.333, samples[7], 1e-3);
		assertEquals(766.6667, samples[8], 1e-3);
		assertEquals(800.0, samples[9], 1e-3);
		
		try {
			vp = SimpleValueProvider.getValueProvider(500, 800, 0);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			vp = SimpleValueProvider.getValueProvider(500, 800, -1);
			fail();
		} catch (IllegalArgumentException e) { }
	}

	
	public final void testGetValueProviderDiscretizacao() {
		
		ValueProvider vp = SimpleValueProvider.getValueProviderDiscretizacao(500, 800, 0.1);
		double[] samples = vp.getValorPerturbado();
		
		assertEquals(7, samples.length);
		
		assertEquals(500.0, samples[0], 1e-3);
		assertEquals(550, samples[1], 1e-3);
		assertEquals(600, samples[2], 1e-3);
		assertEquals(650, samples[3], 1e-3);
		assertEquals(700, samples[4], 1e-3);
		assertEquals(750, samples[5], 1e-3);
		assertEquals(800, samples[6], 1e-3);
		
		try {
			vp = SimpleValueProvider.getValueProviderDiscretizacao(500, 800, 0);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			vp = SimpleValueProvider.getValueProviderDiscretizacao(500, 800, -1);
			fail();
		} catch (IllegalArgumentException e) { }
	}
	
	public final void testGetValueProviderVariacaoPercentual() {
		
		ValueProvider vp = SimpleValueProvider.getValueProviderVariacaoPercentual(0.3, 600, 10);
		double[] samples = vp.getValorPerturbado();
		
		assertEquals(10, samples.length);
		
		assertEquals(420.0, samples[0], 1e-3);
		assertEquals(460.0, samples[1], 1e-3);
		assertEquals(500, samples[2], 1e-3);
		assertEquals(540, samples[3], 1e-3);
		assertEquals(580, samples[4], 1e-3);
		assertEquals(620, samples[5], 1e-3);
		assertEquals(660, samples[6], 1e-3);
		assertEquals(700, samples[7], 1e-3);
		assertEquals(740, samples[8], 1e-3);
		assertEquals(780, samples[9], 1e-3);
		
		try {
			vp = SimpleValueProvider.getValueProviderVariacaoPercentual(0.3, 600, 0);
		} catch (IllegalArgumentException e) { }
		
		try {
			vp = SimpleValueProvider.getValueProviderVariacaoPercentual(0.3, 600, -1);
		} catch (IllegalArgumentException e) { }
	}
}

