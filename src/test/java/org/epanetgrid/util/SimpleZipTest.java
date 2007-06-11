package org.epanetgrid.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import junit.framework.TestCase;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 11/06/2007
 */
public class SimpleZipTest extends TestCase {

	private String tempDir = "zipTempDir";


	public void setUp() {
		File tmpDir = new File(tempDir);
		if(!tmpDir.exists()){
			tmpDir.mkdir();
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		File tmpDir = new File(tempDir);
		tmpDir.delete();
	}

	/** tests zip and unzip functionalities */
	public void testZipItUnzipIt() throws IOException {

		/** tests zipping */
		String[ ] files = { tempDir + File.separator + "dummyFile1", tempDir + File.separator + "dummyFile2" };
		createFiles( files );

		String zipFileName = tempDir + File.separator + "zip1.zip";
		new SimpleZip().zipIt( files, zipFileName );

		File zipFile = new File( zipFileName );
		assertTrue( zipFile.exists() );

		for ( int k = 0; k < files.length; k++ ) {
			File file = new File( files[k] );
			file.delete();

			assertFalse( file.exists() );
		}

		/** tests unzipping */
		new SimpleZip().unzipIt( zipFile, tempDir );

		for ( int k = 0; k < files.length; k++ ) {
			File fileUnzipped = new File( files[k] );
			assertTrue( fileUnzipped.exists() );

			fileUnzipped.delete();
			assertFalse( fileUnzipped.exists() );
		}

		zipFile.delete();
		assertFalse( zipFile.exists() );
	}


	/** creates some files to use in this test */
	private void createFiles( String[ ] filesNames ) throws IOException {

		for ( int k = 0; k < filesNames.length; k++ ) {
			BufferedWriter bw = new BufferedWriter( new FileWriter( filesNames[k] ) );
			bw.write( filesNames[k] );
			bw.write( filesNames[k] );
			bw.write( filesNames[k] );
			bw.write( filesNames[k] );
			bw.close();

			assertTrue( new File( filesNames[k] ).exists() );
		}
	}

}
