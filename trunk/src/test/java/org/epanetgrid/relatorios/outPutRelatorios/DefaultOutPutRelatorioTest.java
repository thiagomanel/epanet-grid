/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 07/08/2007
 */
public class DefaultOutPutRelatorioTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 */
	public final void testBuildDefaultOutPutRelatorio() {
		
		try {
			new DefaultOutPutRelatorio.Builder().setPressaoMaximaPattern("").build(null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		//anyone pattern was set
		try {
			new DefaultOutPutRelatorio.Builder().build(EasyMock.createMock(LinedTextFileDoc.Builder.class));
			fail();
		} catch (IllegalStateException e) { }
		
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#getNumAlarmes(org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo)}.
	 */
	public final void testGetNumAlarmes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#getNumTotalAlarmes()}.
	 */
	public final void testGetNumTotalAlarmes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#pressaoMinimaNode()}.
	 */
	public final void testPressaoMinimaNode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#pressaoMaximaNode()}.
	 */
	public final void testPressaoMaximaNode() {
		fail("Not yet implemented"); // TODO
	}

}
