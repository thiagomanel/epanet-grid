/**
 * 
 */
package org.epanetgrid.main;

import java.io.File;
import java.util.List;

import org.epanetgrid.grid.GridFacade;

/**
 * @author thiagoepdc
 *
 */
public class EpanetGridMain {

	public static void main(String[] args) {
		
		String basePath = "resources"+File.separator+"epanetgrid_source";
		
		GridFacade gridFacade = new GridFacade
				.Builder(basePath)
//				.addLibrary("serialization.jar")
//				.addLibrary("xpp3-1.1.3.4d_b4_min.jar")
//				.addLibrary("xstream-1.1.2.jar")
				.addLibrary("org-epanetgrid.jar")
//				.addLibrary("ourgrid_3.3.jar")
//				.addResource(new File(basePath))
				.build();
		
		gridFacade.addNetWorkFile(basePath+File.separator+"MalhaTeste1-1.inp");
		
		List result = gridFacade.execute();
		System.out.println("execution done! - resultSize "+result.size());
	}
	
}
