package org.epanetgrid.util.packagemanager;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class TarPackageManagerTest extends TestCase {

	private static final String fs = File.separator;
	private static final String testDirectory = "resources"+ fs +"test" + fs + 
					"packageManager";

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		new File(testDirectory + fs + "teste2.tar.gz").delete();
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void testUnpackTarGzFile() throws IOException {
		//-- scenario
		String fileToUnpackName = testDirectory + fs + "teste.tar.gz";
		String folderName = testDirectory + fs + "teste1";
		String folderNameRef = testDirectory + fs + "testeRef";
		
		File fileToUnpack = new File( fileToUnpackName ).getAbsoluteFile();
		File destinyDirOfUnpack = new File( folderName );
		File destinyDirOfUnpack2 = new File( folderNameRef );

		assertTrue(fileToUnpack.exists());

		PackageManager pm = new PackageManagerImpl();
		pm.unpackIt(destinyDirOfUnpack.getAbsolutePath(), fileToUnpack);

		//-- verifying
		PackageManagerTestUtil pmtu = new PackageManagerTestUtil();
		pmtu.compareFiles(destinyDirOfUnpack, destinyDirOfUnpack2);

	}

	/**
	 * @throws IOException 
	 * 
	 */
	public void testUnpackAndPackTarGzFile() throws IOException {
		//-- scenario
		String fileToUnpackName = testDirectory + fs + "teste.tar.gz";
		String destinyTarName = testDirectory + fs + "teste2.tar.gz";
		String folderName = testDirectory + fs + "teste1";
		String folderName2 = testDirectory + fs + "teste2";
		
		File fileToUnpack = new File( fileToUnpackName ).getAbsoluteFile();
		File destinyZipFile = new File( destinyTarName );
		File destinyDirOfUnpack = new File( folderName );
		File destinyDirOfUnpack2 = new File( folderName2 );

		assertTrue(fileToUnpack.exists());

		PackageManager pm = new PackageManagerImpl();
		pm.unpackIt(destinyDirOfUnpack.getAbsolutePath(), fileToUnpack);
		pm.packIt( destinyTarName, PackTypes.TAR_GZ, testDirectory + fs + "teste1" + File.separator + "JOILSON");
		pm.unpackIt(destinyDirOfUnpack2.getAbsolutePath(), destinyZipFile);

		//-- verifying
		PackageManagerTestUtil pmtu = new PackageManagerTestUtil();
		pmtu.compareFiles(destinyDirOfUnpack, destinyDirOfUnpack2);

	}
}
