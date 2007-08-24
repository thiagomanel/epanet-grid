/**
 * 
 */
package org.epanetgrid.relatorios.outPutRelatorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.relatorios.common.IDocItem;
import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
import org.epanetgrid.relatorios.common.RegionMatcher;
import org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 07/08/2007
 */
public class DefaultOutPutRelatorioTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws FileNotFoundException 
	 */
	public final void testBuildDefaultOutPutRelatorio() throws FileNotFoundException {
		try {
			new DefaultOutPutRelatorio.Builder().setPressaoMaximaPattern("").build(null);
			fail();
		} catch (IllegalArgumentException e) { }
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#getNumAlarmes(org.epanetgrid.relatorios.outPutRelatorios.IAlarme.Tipo)}.
	 * @throws IOException 
	 * @throws IOException 
	 */
	public final void testGetNumAlarmes() throws IOException{
		
		LinedTextFileDoc linetxt = EasyMock.createMock(LinedTextFileDoc.class);
		
		IDocItem doc1 = EasyMock.createMock(IDocItem.class);
		IDocItem doc2 = EasyMock.createMock(IDocItem.class);

		String numAlarmsPattern = "Negative pressures";
		IMatcher matcher = new RegexMatcher(numAlarmsPattern);
		
		EasyMock.replay(doc1);
		EasyMock.replay(doc2);
		
		Map<IMatcher, Collection<IDocItem>> result = new HashMap<IMatcher, Collection<IDocItem>>();
		
		Collection<IDocItem> docs = new LinkedList<IDocItem>();
		docs.add(doc1);
		docs.add(doc2);
		
		result.put(matcher, docs);
		
		EasyMock.expect(linetxt.getDocItems()).andReturn(result);
		EasyMock.replay(linetxt);
		
		LinedTextFileDoc.Builder linedtxtBuilder = EasyMock.createNiceMock(LinedTextFileDoc.Builder.class);
		
		EasyMock.expect(linedtxtBuilder.build()).andReturn(linetxt).once();
		EasyMock.replay(linedtxtBuilder);
		
		DefaultOutPutRelatorio outRel = new DefaultOutPutRelatorio.Builder().setPressaoNegativaAlarmPattern(numAlarmsPattern)
											.build(linedtxtBuilder);
		
		assertEquals(2, outRel.getNumAlarmes(Tipo.PRESSAO_NEGATIVA_NO));
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#getNumTotalAlarmes()}.
	 * @throws IOException 
	 */
	public final void testGetNumTotalAlarmes() throws IOException {

		LinedTextFileDoc linetxt = EasyMock.createMock(LinedTextFileDoc.class);
		
		IDocItem doc1 = EasyMock.createMock(IDocItem.class);
		IDocItem doc2 = EasyMock.createMock(IDocItem.class);

		String numAlarmsPattern = "WARNING";
		IMatcher matcher = new RegexMatcher(numAlarmsPattern);
		
		EasyMock.replay(doc1);
		EasyMock.replay(doc2);
		
		Map<IMatcher, Collection<IDocItem>> result = new HashMap<IMatcher, Collection<IDocItem>>();
		
		Collection<IDocItem> docs = new LinkedList<IDocItem>();
		docs.add(doc1);
		docs.add(doc2);
		
		result.put(matcher, docs);
		
		EasyMock.expect(linetxt.getDocItems()).andReturn(result);
		EasyMock.replay(linetxt);
		
		LinedTextFileDoc.Builder linedtxtBuilder = EasyMock.createNiceMock(LinedTextFileDoc.Builder.class);
		
		EasyMock.expect(linedtxtBuilder.build()).andReturn(linetxt).once();
		EasyMock.replay(linedtxtBuilder);
		
		DefaultOutPutRelatorio outRel = new DefaultOutPutRelatorio.Builder().setTotalAlarmesPattern(numAlarmsPattern)
											.build(linedtxtBuilder);
		
		assertEquals(2, outRel.getNumTotalAlarmes());
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#pressaoMinimaNode()}.
	 * @throws IOException 
	 */
	public final void testPressaoMinimaNode() throws IOException {
		
		LinedTextFileDoc linetxt = EasyMock.createMock(LinedTextFileDoc.class);
		
		IDocItem doc1 = EasyMock.createMock(IDocItem.class);
		IDocItem doc2 = EasyMock.createMock(IDocItem.class);

		IMatcher matcher = EasyMock.createMock(RegionMatcher.class);
		
		EasyMock.expect(doc1.getSource()).andReturn("T1                  42.77      1.36     15.62");
		EasyMock.expect(doc2.getSource()).andReturn("T4                  48.55      0.99      6.66");
		
		EasyMock.replay(doc1);
		EasyMock.replay(doc2);
		
		Map<IMatcher, Collection<IDocItem>> result = new HashMap<IMatcher, Collection<IDocItem>>();
		
		Collection<IDocItem> docs = new LinkedList<IDocItem>();
		docs.add(doc1);
		docs.add(doc2);
		
		result.put(matcher, docs);
		
		EasyMock.expect(linetxt.getDocItems()).andReturn(result);
		EasyMock.replay(linetxt);
		
		LinedTextFileDoc.Builder linedtxtBuilder = EasyMock.createNiceMock(LinedTextFileDoc.Builder.class);
		
		EasyMock.expect(linedtxtBuilder.build()).andReturn(linetxt).once();
		EasyMock.replay(linedtxtBuilder);
		
		DefaultOutPutRelatorio outRel = new DefaultOutPutRelatorio.Builder().build(linedtxtBuilder);
		
		assertEquals("T4", outRel.pressaoMinimaNode().getNodeName());
		assertEquals(6.66, outRel.pressaoMinimaNode().getPressao().getExactValue());
		
		assertEquals("T1", outRel.pressaoMaximaNode().getNodeName());
		assertEquals(15.66, outRel.pressaoMaximaNode().getPressao().getExactValue());
	}

	/**
	 * Test method for {@link org.epanetgrid.relatorios.outPutRelatorios.DefaultOutPutRelatorio#pressaoMaximaNode()}.
	 */
	public final void testPressaoMaximaNode() {
			fail("Not yet implemented"); // TODO
	}

}
