/**
 * 
 */
package org.epanetgrid.model.report;

import java.util.Map;


/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 01/05/2007
 */
public interface IReport {

	/**
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value);
	
	/**
	 * @return
	 */
	public Map<String, String> getValues();
	
	/**
	 * @param key
	 * @return
	 */
	public String getValue(String key);
}
