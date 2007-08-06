/**
 * 
 */
package org.epanetgrid.relatorios.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 31/07/2007
 */
public class LinedTextFileDocTest extends TestCase {

	/**
	 * avvva 
	 * omomomo
	 * CaasC
	 * 123Hei
	 */
	private final static String inputFile = "resources"+File.separator+"input";
	
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
			new LinedTextFileDoc.Builder(new File("file_does_not_exists")).build();
			fail();
		} catch (FileNotFoundException e) { }
		
		try {
			File file = EasyMock.createMock(File.class);
			EasyMock.replay(file);
			new LinedTextFileDoc.Builder(file).addMatcher(null);
			fail();
		} catch (IllegalArgumentException e) { }
		
		
		try {
			File file = EasyMock.createMock(File.class);
			IMatcher goodMatcher = EasyMock.createMock(IMatcher.class);
			EasyMock.replay(file);
			EasyMock.replay(goodMatcher);
			new LinedTextFileDoc.Builder(file)
						.addMatcher(goodMatcher)
						.addMatcher(null);
			fail();
			
		} catch (IllegalArgumentException e) { }
	}
	
	public void testDuplicatedMatcher() {
		
		//same object
		try {
			File file = EasyMock.createMock(File.class);
			IMatcher goodMatcher = new RegexMatcher("a");
			new LinedTextFileDoc.Builder(file)
						.addMatcher(goodMatcher)
						.addMatcher(goodMatcher);
			fail();
			
		} catch (IllegalArgumentException e) { }
		
		//the objects are equals
		try {
			File file = EasyMock.createMock(File.class);
			IMatcher goodMatcher = new RegexMatcher("a");
			IMatcher equalsMatcher = new RegexMatcher("a");
			new LinedTextFileDoc.Builder(file)
						.addMatcher(goodMatcher)
						.addMatcher(equalsMatcher);
			fail();
			
		} catch (IllegalArgumentException e) { }
	}
	
	public void testGetDocItems() throws IOException{

		/**
		 * avvva 
		 * omomomo
		 * CaasC
		 * 123Hei
		 */
		IMatcher matcher = new RegexMatcher(".*om.*");
		
		LinedTextFileDoc lineTex = new LinedTextFileDoc.Builder(new File(inputFile)).addMatcher(matcher).build();
		
		Map<IMatcher, Collection<IDocItem>> result =  lineTex.getDocItems();
		
		assertEquals(1, result.keySet().size());
		Collection<IDocItem> docItems = result.get(result.keySet().iterator().next());
		assertEquals(1, docItems.size());
		
		assertEquals(new DefaultDocItem("omomomo"), docItems.iterator().next());
	}
	
	public void testGetDocItemsWithMultipleMatchers() throws IOException{
		fail();
		
		
		/**
		 * avvva 
		 * omomomo
		 * CaasC
		 * 123Hei
		 */
		IMatcher matcher = new RegexMatcher(".*om.*");
		IMatcher matcher2 = new RegexMatcher(".*123.*");
		
		LinedTextFileDoc lineTex = new LinedTextFileDoc.Builder(new File(inputFile))
															.addMatcher(matcher)
															.addMatcher(matcher2)
															.build();
		
		Map<IMatcher, Collection<IDocItem>> result =  lineTex.getDocItems();
		
		assertEquals(1, result.keySet().size());
		Collection<IDocItem> docItems = result.get(result.keySet().iterator().next());
		assertEquals(1, docItems.size());
		
		assertEquals(new DefaultDocItem("omomomo"), docItems.iterator().next());
	}

}
