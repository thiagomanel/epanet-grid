package org.epanetgrid.util;

import org.easymock.EasyMock;
import org.epanetgrid.model.NetworkComponent;

import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class NetWorkComponentStringComparatorTest extends TestCase {

	/*
	 * Test method for 'org.epanetgrid.util.NetWorkComponentStringComparator.compare(NetworkComponent<?>, NetworkComponent<?>)'
	 */
	public void testCompare() {

		NetWorkComponentStringComparator netComparator = new NetWorkComponentStringComparator();
		
		NetworkComponent na = EasyMock.createMock(NetworkComponent.class);
		EasyMock.expect(na.label()).andReturn("a").anyTimes();
		
		NetworkComponent naEQ = EasyMock.createMock(NetworkComponent.class);
		EasyMock.expect(naEQ.label()).andReturn("a").anyTimes();
		
		NetworkComponent na1 = EasyMock.createMock(NetworkComponent.class);
		EasyMock.expect(na1.label()).andReturn("a1").anyTimes();
		
		NetworkComponent na5 = EasyMock.createMock(NetworkComponent.class);
		EasyMock.expect(na5.label()).andReturn("a5").anyTimes();
		
		NetworkComponent na3d = EasyMock.createMock(NetworkComponent.class);
		EasyMock.expect(na3d.label()).andReturn("a001").anyTimes();
		
		NetworkComponent na2 = EasyMock.createMock(NetworkComponent.class);
		EasyMock.expect(na2.label()).andReturn("a2").anyTimes();
		
		NetworkComponent nb = EasyMock.createMock(NetworkComponent.class);
		EasyMock.expect(nb.label()).andReturn("b").anyTimes();
		
		EasyMock.replay(na);
		EasyMock.replay(naEQ);
		EasyMock.replay(na1);
		EasyMock.replay(na2);
		EasyMock.replay(na5);
		EasyMock.replay(na3d);
		EasyMock.replay(nb);
		
		assertTrue(netComparator.compare(na, na1) < 0 );
		assertTrue(netComparator.compare(na1, na) > 0 );
		
		assertTrue(netComparator.compare(na1, na2) < 0 );
		assertTrue(netComparator.compare(na2, na1) > 0 );
		
		assertTrue(netComparator.compare(na, na2) < 0 );
		assertTrue(netComparator.compare(na2, na) > 0 );
		
		assertTrue(netComparator.compare(na, naEQ) == 0 );
		assertTrue(netComparator.compare(naEQ, na) == 0 );
	}

}
