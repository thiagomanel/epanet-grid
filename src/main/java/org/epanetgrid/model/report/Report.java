/**
 * 
 */
package org.epanetgrid.model.report;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 * since 01/05/2007
 */
public class Report implements IReport {
	
	private final Map<String, String> values = new HashMap<String, String>();
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.report.IReport#getValues()
	 */
	public Map<String, String> getValues(){
		return Collections.unmodifiableMap(values);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.report.IReport#setValue(java.lang.String, java.lang.String)
	 */
	public void setValue(String key, String value){
		values.put(key, value);
	}
	
	/* (non-Javadoc)
	 * @see org.epanetgrid.model.report.IReport#getValue(java.lang.String)
	 */
	public String getValue(String key) {
		return values.get(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Report) {
			final Report other = (Report) obj;
			return values.equals(other.values);
		}
		return false;
	}
}
