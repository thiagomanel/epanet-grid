package org.epanetgrid.perturbador;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.perturbador.valueProvider.ValueProvider;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 01/09/2007
 */
public class DefaultVariavelPerturbadaTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public final void testDefaultVariavelPerturbada() {
		
		ValueProvider vProvider = EasyMock.createMock(ValueProvider.class);
		double[] values = new double[] {1.0, 1.2};
		EasyMock.expect(vProvider.getValorPerturbado()).andReturn(values).anyTimes();
		EasyMock.replay(vProvider);
		
		try {
			new DefaultVariavelPerturbada(null, vProvider);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			new DefaultVariavelPerturbada("foo", null);
			fail();
		} catch (IllegalArgumentException e) { }
		
	}


	public final void testGetNumSamples() {
		
		ValueProvider vProvider = EasyMock.createMock(ValueProvider.class);
		double[] values = new double[] {1.0, 1.2};
		EasyMock.expect(vProvider.getValorPerturbado()).andReturn(values).anyTimes();
		EasyMock.replay(vProvider);
		
		int samplesSize = values.length;
		
		assertEquals(samplesSize, new DefaultVariavelPerturbada("foo", vProvider).getNumSamples());
		
	}

}
