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

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;

/**
 * This class is supposed to zip files into a .zip file and extract them 
 * creating parent directories. Algorithms used here are based on the ones at
 * http://www.javalobby.org/java/forums/t16299.html
 * 
 * @version 2.0 Created on 08/02/2006
 */
public class ZipUtil extends BaseZip{

	public ZipUtil(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.ourgrid.common.util.BaseZip#createUnzipFileEntry(java.lang.String, java.util.zip.ZipEntry)
	 */
	@Override
	protected File createUnzipFileEntry(String destinationDirectory, ZipEntry entry) {
		return new File( destinationDirectory, entry.getName());
	}
	
	/**
	 * Main
	 * 
	 * @param args The first parameter is either "-c" or "-x" (to compress or
	 *        extract, respectively). If "-c", the second parameter is the zip
	 *        file path, and the others are file paths to compress (separated by
	 *        space). If "-x", the second parameter is the zip file path to
	 *        unzip, and the other is the destination directory where the will
	 *        be placed.
	 */
	public static void main( String[ ] args ) {
		ZipUtil zipper = new ZipUtil();
		try {
			if ( args[0].equals( "-c" ) ) {
				String[ ] files = new String[ args.length - 2 ];
				for ( int k = 2; k < args.length; k++ ) {
					files[k - 2] = args[k];
				}
				zipper.zipIt( files, args[1] );
			} else if ( args[0].equals( "-x" ) ) {
				zipper.unzipIt( new File( args[1] ), args[2] );

			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
