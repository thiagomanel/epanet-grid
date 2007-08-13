package org.epanetgrid.relatorios.resultRelatorios;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.epanetgrid.relatorios.common.IDocItem;
import org.epanetgrid.relatorios.common.IMatcher;
import org.epanetgrid.relatorios.common.LinedTextFileDoc;
import org.epanetgrid.relatorios.common.RegexMatcher;
import org.jscience.economics.money.Money;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 11/08/2007
 */
public class DefaultResultRelatorioTest extends TestCase {

	
	public final void testBuilder() throws IOException, ParseException{
		
		//testar IO clauses
		//testar parse exceptions
		
		try {
			new DefaultResultRelatorio.Builder().setDataSimulacaoPattern("").build(null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		//anyone pattern was set
		try {
			new DefaultResultRelatorio.Builder().build(EasyMock.createMock(LinedTextFileDoc.Builder.class));
			fail();
		} catch (IllegalStateException e) { }
	}
	
	/*
	 * Test method for 'org.epanetgrid.relatorios.resultRelatorios.DefaultResultRelatorio.getDataSimulacao()'
	 */
	public final void testGetDataSimulacao() throws IOException, ParseException {

		LinedTextFileDoc linetxt = EasyMock.createMock(LinedTextFileDoc.class);
		
		IDocItem doc1 = EasyMock.createMock(IDocItem.class);

		String dataSimulacaoPattern = ".*Page 1.*";
		IMatcher matcher = new RegexMatcher(dataSimulacaoPattern);

		EasyMock.expect(doc1.getSource()).andReturn("  Page 1  Thu Aug 24 19:17:46 2006 ");
		EasyMock.replay(doc1);
		
		Map<IMatcher, Collection<IDocItem>> result = new HashMap<IMatcher, Collection<IDocItem>>();
		
		Collection<IDocItem> docs = new LinkedList<IDocItem>();
		docs.add(doc1);
		
		result.put(matcher, docs);
		
		EasyMock.expect(linetxt.getDocItems()).andReturn(result);
		EasyMock.replay(linetxt);
		
		LinedTextFileDoc.Builder linedtxtBuilder = EasyMock.createNiceMock(LinedTextFileDoc.Builder.class);
		
		EasyMock.expect(linedtxtBuilder.build()).andReturn(linetxt).once();
		EasyMock.replay(linedtxtBuilder);
		
		DefaultResultRelatorio outRel = new DefaultResultRelatorio.Builder().setDataSimulacaoPattern(dataSimulacaoPattern)
											.build(linedtxtBuilder);
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(2006, Calendar.AUGUST, 24, 19, 17, 46);
		
		Date expectDate = calendar.getTime();
		Date resultDate = outRel.getDataSimulacao();
		assertEquals(expectDate.getYear(), resultDate.getYear());
		assertEquals(expectDate.getMonth(), resultDate.getMonth());
		assertEquals(expectDate.getDay(), resultDate.getDay());
		assertEquals(expectDate.getHours(), resultDate.getHours());
		assertEquals(expectDate.getMinutes(), resultDate.getMinutes());
		assertEquals(expectDate.getSeconds(), resultDate.getSeconds());
	}

	/*
	 * Test method for 'org.epanetgrid.relatorios.resultRelatorios.DefaultResultRelatorio.getCusto()'
	 */
	public final void testGetCusto() throws IOException, ParseException {
		
		LinedTextFileDoc linetxt = EasyMock.createMock(LinedTextFileDoc.class);
		
		IDocItem doc1 = EasyMock.createMock(IDocItem.class);

		String custoSimulacaoPattern = ".*Total Cost.*";
		IMatcher matcher = new RegexMatcher(custoSimulacaoPattern);

		EasyMock.expect(doc1.getSource()).andReturn("  Total Cost: 1500.15 ");
		EasyMock.replay(doc1);
		
		Map<IMatcher, Collection<IDocItem>> result = new HashMap<IMatcher, Collection<IDocItem>>();
		
		Collection<IDocItem> docs = new LinkedList<IDocItem>();
		docs.add(doc1);
		
		result.put(matcher, docs);
		
		EasyMock.expect(linetxt.getDocItems()).andReturn(result);
		EasyMock.replay(linetxt);
		
		LinedTextFileDoc.Builder linedtxtBuilder = EasyMock.createNiceMock(LinedTextFileDoc.Builder.class);
		
		EasyMock.expect(linedtxtBuilder.build()).andReturn(linetxt).once();
		EasyMock.replay(linedtxtBuilder);
		
		DefaultResultRelatorio outRel = new DefaultResultRelatorio.Builder().setCustoSimulacaoPattern(custoSimulacaoPattern)
											.build(linedtxtBuilder);
		
		assertEquals(Measure.valueOf(1500.15, Money.BASE_UNIT), outRel.getCusto());
	}

}
