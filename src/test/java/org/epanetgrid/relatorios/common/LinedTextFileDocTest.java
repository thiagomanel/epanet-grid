/**
 * 
 */
package org.epanetgrid.relatorios.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public class LinedTextFileDocTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testWrongConstruction(){
		
		try {
			new LinedTextFileDoc.Builder(null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		try {
			Iterable<String> source = EasyMock.createMock(Iterable.class);
			EasyMock.replay(source);
			new LinedTextFileDoc.Builder(source).addMatcher(null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		
		try {
			Iterable<String> source = EasyMock.createMock(Iterable.class);
			IMatcher goodMatcher = EasyMock.createMock(IMatcher.class);
			EasyMock.replay(source);
			EasyMock.replay(goodMatcher);
			new LinedTextFileDoc.Builder(source)
						.addMatcher(goodMatcher)
						.addMatcher(null);
			fail();
		} catch (IllegalArgumentException e) { }
	}
	
	public void testGetDocItems(){
		
		Iterable<String> source = EasyMock.createMock(Iterable.class);
		Iterator<String> iter = EasyMock.createMock(Iterator.class);
		
		EasyMock.expect(iter.hasNext()).andReturn(true);
		EasyMock.expect(iter.next()).andReturn("baa");
		EasyMock.expect(iter.hasNext()).andReturn(false);
		EasyMock.expect(iter.next()).andReturn("foo");
		
		EasyMock.expect(source.iterator()).andReturn(iter);
		
		EasyMock.replay(iter);
		EasyMock.replay(source);
		
		IMatcher matcher = EasyMock.createMock(IMatcher.class);
		EasyMock.expect(matcher.match("baa")).andReturn(true);
		EasyMock.expect(matcher.match("foo")).andReturn(false);
		
		EasyMock.replay(matcher);
		
		LinedTextFileDoc lineTex = new LinedTextFileDoc.Builder(source).addMatcher(matcher).build();
		
		Map<IMatcher, Collection<IDocItem>> result =  lineTex.getDocItems();
		
		assertEquals(1, result.keySet().size());
		Collection<IDocItem> docItems = result.get(result.keySet().iterator().next());
		assertEquals(1, docItems.size());
		
		assertEquals(new DefaultDocItem("baa"), docItems.iterator().next());
	}

}
