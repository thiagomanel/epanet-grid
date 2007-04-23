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
	}

}

