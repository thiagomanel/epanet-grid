package org.epanetgrid.util.packagemanager;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class ZipPackageManagerTest extends TestCase {

	private static final String fs = File.separator;
	private static final String testDirectory = "resources"+ fs +"test" + fs + 
					"packageManager";
	
	ZipPackageManager zipManager;

	protected void setUp() {
		zipManager = new ZipPackageManager();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void testUnpackZipFile() throws IOException {
		//-- scenario
		String fs = File.separator;
		String fileToUnpackName = testDirectory + fs + "teste.zip";
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
	public void testUnpackAndPackZipFile() throws IOException {
		//-- scenario
		String fileToUnpackName = testDirectory + fs + "teste.zip";
		String destinyZipName = testDirectory + fs + "teste2.zip";
		String folderName = testDirectory + fs + "teste1";
		String folderName2 =testDirectory + fs + "teste2";
		
		File fileToUnpack = new File( fileToUnpackName ).getAbsoluteFile();
		File destinyZipFile = new File( destinyZipName );
		File destinyDirOfUnpack = new File( folderName );
		File destinyDirOfUnpack2 = new File( folderName2 );

		assertTrue(fileToUnpack.exists());

		PackageManager pm = new PackageManagerImpl();
		pm.unpackIt(destinyDirOfUnpack.getAbsolutePath(), fileToUnpack);
		pm.packIt( destinyZipName, PackTypes.ZIP, testDirectory + fs + "teste1" + File.separator + "JOILSON");
		pm.unpackIt(destinyDirOfUnpack2.getAbsolutePath(), destinyZipFile);

		//-- verifying
		PackageManagerTestUtil pmtu = new PackageManagerTestUtil();
		pmtu.compareFiles(destinyDirOfUnpack, destinyDirOfUnpack2);

	}
}
