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
package org.epanetgrid.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * This class is supposed to zip files into a .zip file and extract them.
 * 
 * @version 2.0 Created on 08/02/2006
 */
public abstract class BaseZip {

	/** a buffer */
	protected static final int BUFFER = 2048;

	protected BaseZip(){ }

	/**
	 * Zips files.
	 * 
	 * @param filesToZip Filepaths to zip.
	 * @param zipFileName The zip file path with respective name.
	 * @throws IOException Thrown if an IO exception occurs while zipping files.
	 */
	public void zipIt( String[ ] filesToZip, String zipFileName ) throws IOException {

		ZipOutputStream out = null;
		InputStream in = null; // Used to close other input stream in case of
								// exceptions
		try {
			byte[ ] buf = new byte[ BUFFER ];

			File outputFile = new File( zipFileName );

			OutputStream rawOut = new BufferedOutputStream( new FileOutputStream( outputFile ) );
			out = new ZipOutputStream( rawOut );
			out.setLevel( Deflater.BEST_COMPRESSION );

			// Iterate through the array of files, adding each to the zip file
			for ( String fileToZip : filesToZip ) {
				InputStream rawIn = new FileInputStream( fileToZip );
				in = new BufferedInputStream( rawIn );

				ZipEntry entry = new ZipEntry( fileToZip );
				out.putNextEntry( entry );

				// Really writes to the zip file
				int len;
				while ( (len = in.read( buf )) > 0 ) {
					out.write( buf, 0, len );
				}

				// Close the current entry
				out.closeEntry();
			}
		} finally {
			if ( in != null ) {
				in.close();
			}
			if ( out != null ) {
				out.close();
			}
		}
	}


	/**
	 * Unzips <b>without</b> creating parent directories.
	 * 
	 * @param file The zip file to be extracted.
	 * @param destinationDirectory The destination directory where extracted
	 *        files will be placed.
	 * @throws IOException Thrown if an IO exception occurs while unzipping
	 *         files.
	 */
	public void unzipIt( File file, String destinationDirectory ) throws IOException {

		ZipFile zipFile = new ZipFile( file, ZipFile.OPEN_READ );

		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while ( entries.hasMoreElements() ) {
			ZipEntry entry = entries.nextElement();

			/**
			 *	Overwrite this method for junk behavior. 
			 *	The zip/unzip junk behavior means do not make directories  
			 */
			File entryDestination = createUnzipFileEntry(destinationDirectory, entry);

			// This file may be in a subfolder in the Zip bundle
			// This line ensures the parent folders are all
			// created.
			entryDestination.getParentFile().mkdirs();

			// Directories are included as seperate entries
			// in the zip file.
			if ( !entry.isDirectory() ) {
				generateFile( entryDestination, entry, zipFile );
			}
		}
	}


	/**
	 * @param destinationDirectory
	 * @param entry
	 * @return
	 */
	protected abstract File createUnzipFileEntry(String destinationDirectory, ZipEntry entry);

	/**
	 * Generates a file according to its <code>ZipEntry</code> and the file it
	 * was zipped on.
	 * 
	 * @param fileToCreate File to be created.
	 * @param entry Zip entry.
	 * @param zipFile The zip file.
	 * @throws IOException Thrown if an IO exception occurs while generating the
	 *         file.
	 */
	private void generateFile( File fileToCreate, ZipEntry entry, ZipFile zipFile ) throws IOException {

		InputStream in = null;
		OutputStream out = null;
		try {
			InputStream rawIn = zipFile.getInputStream( entry );
			in = new BufferedInputStream( rawIn );

			FileOutputStream rawOut = new FileOutputStream( fileToCreate );
			out = new BufferedOutputStream( rawOut );

			// pump data from zip file into new files
			byte[ ] buf = new byte[ BUFFER ];
			int len;
			while ( (len = in.read( buf )) > 0 ) {
				out.write( buf, 0, len );
			}
		} finally {
			if ( in != null ) {
				in.close();
			}
			if ( out != null ) {
				out.close();
			}
		}
	}

}
