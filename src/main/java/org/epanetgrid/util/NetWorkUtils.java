/**
 * 
 */
package org.epanetgrid.util;

import org.epanetgrid.model.epanetNetWork.NetWork;
import org.epanetgrid.resultado.output.DateTimeInterval;

/**
 * @author alan
 *
 */
public class NetWorkUtils {
	
	public static int getIndice(NetWork network, DateTimeInterval dateTime) {
		long d1 = (network.getStartClockTime().getHour() * 60 * 60 * 1000) + 
				(network.getStartClockTime().getMinutes() * 60 * 1000);

		long d2 = (dateTime.getHour() * 60 * 60 * 1000) + 
				(dateTime.getMinutes() * 60 * 1000);
		
		return (int)( (d2 - d1) / network.getHydraulicTimestep().getMillis() );
	}
	
}
