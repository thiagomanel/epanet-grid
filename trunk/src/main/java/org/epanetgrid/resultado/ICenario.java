/**
 * 
 */
package org.epanetgrid.resultado;

import java.util.List;

import org.epanetgrid.relatorios.outPutRelatorios.IAlarme;
import org.joda.time.DateTime;

/**
 * @author alan
 *
 */
public interface ICenario {

	public DateTime getData();
	
	public List<IAlarme> getAlarmes();
	
}
