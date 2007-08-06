package org.epanetgrid.relatorios.common;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 06/08/2007
 */
public class DefaultDocItemTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.relatorios.common.DefaultDocItem.equals(Object)'
	 */
	public void testEqualsObject() {
		
		DefaultDocItem docItem = new DefaultDocItem("source");
		
		//same
		assertTrue(docItem.equals(docItem));
		
		//equals
		assertTrue(docItem.equals(new DefaultDocItem("source")));

		//null semantic
		assertFalse(docItem.equals(null));
	}
	
	//equals object must have the same hashcode
	public void testHashCode(){
		
		DefaultDocItem docItem = new DefaultDocItem("source");
		
		//same
		assertEquals(docItem.hashCode(), docItem.hashCode());
		
		//equals
		assertEquals(docItem.hashCode(), new DefaultDocItem("source").hashCode());
	}

}
