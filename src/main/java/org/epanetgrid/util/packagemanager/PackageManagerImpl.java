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
 * This class is an implementation of PackageManager interface which provides
 * the operations of packing and unpacking files in a generic format.
 * The supported formats can be found in the PackTypes class.
 */
public class PackageManagerImpl implements PackageManager{
	
	/**
	 * This method provides an generic unpacking opeation. The manager verifies
	 * the packing type and unpack it using this information.
	 * @param destinationDirectory the destiny directory where to the files will be
	 * unpacked.
	 * @param file the file to be unpacked.
	 * @throws IOException if any I/O error occur during this operation.
	 */
	public void unpackIt(String destinationDirectory, File fileToPack)
	throws IOException {
		if (ZipPackageManager.isZipFile(fileToPack.getAbsolutePath())) {
			ZipPackageManager zipManager = new ZipPackageManager();
			zipManager.unpackIt(fileToPack, destinationDirectory);
		} else if (TarPackageManager.isTarGzFile(fileToPack)) {
			TarPackageManager tarManager = new TarPackageManager();
			tarManager.unpackIt(destinationDirectory, fileToPack);
		}
	}

	/**
	 * This method provides an generic packing operation. If the specified packtype 
	 * is a compressing algotithm, then the file will be compressed if possible.
	 * @param fileName the result file of the package operation.
	 * @param packType the packing type.
	 * @param fileToPack the file to be packaged. It can also be a directory and it
	 * will be packaged recursively.
	 * @throws IOException if any I/O error occur during this operation.
	 */
	public void packIt(String destinationDirectory, PackTypes packType, String fileToPack) 
	throws IOException {
		if (packType.isZip()) {
			ZipPackageManager zipManager = new ZipPackageManager();
			zipManager.packIt(new File(destinationDirectory), new File(fileToPack));
		} else if (packType.isTarGz()) {
			TarPackageManager tarManager = new TarPackageManager();
			tarManager.packIt(destinationDirectory, fileToPack);
		}
	}

}
