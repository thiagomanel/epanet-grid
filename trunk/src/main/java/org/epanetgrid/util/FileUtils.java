/**
 * 
 */
package org.epanetgrid.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public class FileUtils {

	/**
	 * @param zipFilename
	 * @param filenames
	 * @throws IOException
	 */
	public static void zip(String zipFilename, String[] filenames) 
	throws IOException {
		zip(zipFilename, filenames, filenames);  
	}

	/**
	 * @param zipFilename
	 * @param filenames
	 * @param archFilenames
	 * @throws IOException
	 */
	public static void zip(
			String zipFilename, String[] filenames, String[] archFilenames
	) throws IOException {

		ZipOutputStream zout = new ZipOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(zipFilename)    
				)
		);

		byte[] data = new byte[512];
		int bc;
		for (int i = 0; i < filenames.length; i++) {
			InputStream fin = new BufferedInputStream(
					new FileInputStream(filenames[i])    
			);

			ZipEntry entry = new ZipEntry(stripPath(archFilenames[i]));
			zout.putNextEntry(entry);

			while ((bc = fin.read(data, 0, 512)) != -1) {
				zout.write(data, 0, bc);
			}
			zout.flush();
		}

		zout.close();  
	}

	/**
	 * @param num
	 * @param baseDir
	 * @param malhaName
	 * @return
	 */
	public static String[] getFileNames(int num, String baseDir, String malhaName) {
		String[] nomes = new String[num];
		for (int i = 0; i < nomes.length; i++) {
			nomes[i] = baseDir+""+malhaName+"-"+(i+1)+".inp";
		}
		return nomes;
	}
	
	/**
	 * @param filename
	 * @return
	 */
	public static String stripPath(String filename) {
		int index = filename.lastIndexOf("\\");
		if (index == -1) return(filename);
		return(filename.substring(index+1));
	}

}
