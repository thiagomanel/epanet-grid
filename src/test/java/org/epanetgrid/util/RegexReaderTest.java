package org.epanetgrid.util; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 04/08/2007
 */
public class RegexReaderTest extends TestCase {

	/**
	 * avvva 
	 * omomomo
	 * CaasC
	 * 123Hei
	 */
	private final static String inputFile = "resources"+File.separator+"input";
	
	public final void testWrongConstructions() {
		
		try {
			new FileReader("filenotfound");
			fail();
		} catch (FileNotFoundException e) { }
		
	}
	
	public final void testReadingWithPatterns() throws FileNotFoundException{
		
		String pattern = "a";
		
		String match1 = "avvva";
		String match2 = "CaasC"; 
		
	    BufferedReader rd = new BufferedReader(new RegexReader(new BufferedReader(new FileReader(inputFile)), pattern));
    
        // Retrieve all lines that match pattern
        try {
			assertEquals(match1, rd.readLine().trim());
			assertEquals(match2, rd.readLine().trim());
			assertNull(rd.readLine());
		} catch (IOException e) {
			fail();
		}
	}
	
	public final void testReadingNoPatternsFound() throws FileNotFoundException{
		
		String pattern = "nopatterns";
		
	    BufferedReader rd = new BufferedReader(new FileReader(inputFile));
        rd = new BufferedReader(new RegexReader(rd, pattern));
    
        // Retrieve all lines that match pattern
        try {
			assertNull(rd.readLine());
		} catch (IOException e) {
			fail();
		}
	}
	
}
