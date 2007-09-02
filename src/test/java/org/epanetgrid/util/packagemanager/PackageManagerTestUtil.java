package org.epanetgrid.util.packagemanager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PackageManagerTestUtil {
	
	/**
	 * This method compares every byte of two files. If any byte is not equals, the method fails.
	 * 
	 * @param file1
	 * @param file2
	 * @throws IOException
	 */
	public boolean compareFiles(File file1, File file2) throws IOException {
		
		boolean isEqual = true;
		
		if (file1.isFile() && file2.isFile()) {
			if (!filesAreEquals(file1, file2)) {
				isEqual = false;
			}
		} else {
			File[] files1 = file1.listFiles();
			File[] files2 = file2.listFiles();
			if (files1.length != files2.length) {
				isEqual = false;
			}
			for (File f : files1) {
				File f2 = findFile(f, files2);
				if (f2 == null) {
					isEqual = false;
				}
				compareFiles(f, f2);
			}
		}
		return isEqual;
	}

	private File findFile(File f, File[] files2) {
		for (File f2 : files2) {
			if (f.getName().equals(f2.getName())) {
				return f2;
			}
		}
		return null;
	}

	public boolean filesAreEquals(File file, File file2) throws IOException {
		BufferedInputStream reader1 = new BufferedInputStream(new FileInputStream(file));
		BufferedInputStream reader2 = new BufferedInputStream(new FileInputStream(file2));

		int i1;
		int i2;
		while ((i1 = reader1.read()) != -1) {
			i2 = reader2.read();
			if (i2 == -1 || i1 != i2) {
				return false;
			}
		}
		if (reader2.read() != -1) {
			return false;
		}
		return true;
	}

}
