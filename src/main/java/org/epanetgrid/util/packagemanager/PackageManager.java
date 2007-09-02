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

import java.io.File;
import java.io.IOException;

/**
 * This interface provides the basic operations for compressing and decompressing
 * files. The implementations of this interface should provide verification of
 * which format is being required for compressing of decompressing the specified
 * file.
 * @author Jemerson Dam�sio - jemersonfd@gmail.com
 */
public interface PackageManager {
	
	/**
	 * This method provides an generic packing operation. If the specified packtype 
	 * is a compressing algotithm, then the file will be compressed if possible.
	 * @param fileName the result file of the package operation.
	 * @param packType the packing type.
	 * @param fileToPack the file to be packaged. It can also be a directory and it
	 * will be packaged recursively.
	 * @throws IOException if any I/O error occur during this operation.
	 */
	public void packIt( String fileName, PackTypes packType, String fileToPack ) throws IOException;
	
	/**
	 * This method provides an generic unpacking opeation. The manager verifies
	 * the packing type and unpack it using this information.
	 * @param destinationDirectory the destiny directory where to the files will be
	 * unpacked.
	 * @param file the file to be unpacked.
	 * @throws IOException if any I/O error occur during this operation.
	 */
	public void unpackIt( String destinationDirectory, File file ) throws IOException; 
	

}
