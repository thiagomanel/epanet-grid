/*
 * Copyright (c) 2002-2006 Universidade Federal de Campina Grande
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.epanetgrid.util.packagemanager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;

/**
 * This class provides the operations of compressing and decompressing 
 * a Tar.GZ file.
 * @author Jemerson Damasio - jemersonfd@gmail.com
 */
public class TarPackageManager {

	/**
	 * This is a standard buffer size for writing bytes on the disk.
	 */
	private static final int BUFFER_SIZE = 4096; // 4 Kb


	public void packIt(String zipFileName, String fileToCompress) throws IOException {
		compress(zipFileName, fileToCompress);
	}

	private void addToTARGZ(File fileToZip, TarOutputStream out, String extractPath) 
	throws IOException {
		if (fileToZip.isDirectory()) {
			File[] files = fileToZip.listFiles();
			
			out.putNextEntry(new TarEntry(fileToZip.getAbsolutePath()));
			out.closeEntry();
			
			for (File toAdd : files) {
				addToTARGZ(toAdd.getAbsoluteFile(), out, extractPath);
			}
		} else {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(fileToZip));
			String newPath = fileToZip.getAbsolutePath().substring(extractPath.length()+1);
			out.putNextEntry(new TarEntry(newPath));
			int len;
			byte[] buf = new byte[BUFFER_SIZE];
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
	}
	
	/**
	 * This method provides an operation of decompressing a standard 
	 * TAR.GZ file to the specified directory.
	 * If the specified directory does no exists it will be created
	 * if possible.
	 * @param file the file under TAR.GZ format to be decompressed.
	 * @param destDir the destination directory.
	 * @throws IOException if any I/O error occur during the extraction.
	 */
	public void unpackIt(String destDir, File file) throws IOException {
		File destinationDirectory = new File(destDir);
		//assuming the file you pass in is not a dir
		destinationDirectory.mkdir();
		//create tar input stream from a .tar.gz file
		TarInputStream tin = 
			new TarInputStream(
					new GZIPInputStream(
							new FileInputStream(file)));

		//get the first entry in the archive                          
		TarEntry tarEntry = tin.getNextEntry();     
		while (tarEntry != null){  
			//create a file with the same name as the tarEntry 
			File destPath = new File(destinationDirectory.toString() + 
					File.separatorChar + tarEntry.getName());
			if (tarEntry.isDirectory()){
				destPath.mkdir();                           
			}
			else {
				FileOutputStream fout = new FileOutputStream(destPath); 
				tin.copyEntryContents(fout);   
				fout.close();                      
			}
			tarEntry = tin.getNextEntry(); 
		}    
		tin.close();
	}

	/**
	 * This methods checks whether the parameter file is under
	 * a tar.gz format.
	 * @param fileToVerify the file to be verified.
	 * @return <code>true</code> if this file is under a tar.gz format,
	 * <code>false</code> otherwise.
	 */
	public static boolean isTarGzFile(File fileToVerify) {
		try {
			TarInputStream tin = 
				new TarInputStream(
						new GZIPInputStream(
								new FileInputStream(fileToVerify)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	/***********/

	ZipOutputStream out;

	FileInputStream in;

	private void compress(String compressedFile, String directoryPath){
		
		try {
			out = new ZipOutputStream(new FileOutputStream(compressedFile));
			this.compressDirectory(directoryPath);
		} catch (Exception c) {
			c.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void compressFile(String filePath) {
		String outPut = filePath + ".zip";
		try {
			FileInputStream in = new FileInputStream(filePath);
			GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(
					outPut));
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = in.read(buffer)) != -1)
				out.write(buffer, 0, bytes_read);
			in.close();
			out.close();
		} catch (Exception c) {
			c.printStackTrace();
		}
	}

	private void compressDirectory(String directoryPath) {
		byte[] buffer = new byte[4096];
		byte [] extra = new byte[0];
		File dir = new File(directoryPath);
		int bytes_read;
		try {
			if (dir.isDirectory()) {
				String[] entries = dir.list();
				if(entries.length == 0)
				{
					ZipEntry entry = new ZipEntry(dir.getPath()+"/");
					out.putNextEntry(entry);
				}
				for (int i = 0; i < entries.length; i++) {
					File f = new File(dir, entries[i]);
					compressDirectory(f.getAbsolutePath());
				}
			} else {
				in = new FileInputStream(dir);
				ZipEntry entry = new ZipEntry(dir.getPath());
				out.putNextEntry(entry);
				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);
				}
				in.close();
			}
		} catch (Exception c) {
			c.printStackTrace();
		}
	}
	
}
