/**
 * 
 */
package org.epanetgrid.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.epanetgrid.grid.GridFacade;
import org.epanetgrid.grid.GridFacade.EpanetGridRunnableResult;

/**
 * @author thiagoepdc
 *
 */
public class EpanetGridMain {

	public static void main(String[] args) throws IOException {
		
		String basePath = "resources"+File.separator+"epanetgrid_source";
		
		GridFacade gridFacade = new GridFacade
				.Builder(basePath)
				.addLibrary("serialization.jar")
				.addLibrary("xpp3-1.1.3.4d_b4_min.jar")
				.addLibrary("xstream-1.1.2.jar")
				.addLibrary("org-epanetgrid.jar")
				.addLibrary("ourgrid_3.3.jar")
				.addResource(new File(basePath+File.separator+"epanetgrid.tar.gz"))
				.build();
		gridFacade.addNetWorkFile(basePath+File.separator+"MalhaTeste1-1.inp", "fooRelatorio");
		
		List result = gridFacade.execute();
		EpanetGridRunnableResult resultado = (EpanetGridRunnableResult) result.get(0);
		System.out.println("execution done! - resultSize "+result.size());
	}
	
}
