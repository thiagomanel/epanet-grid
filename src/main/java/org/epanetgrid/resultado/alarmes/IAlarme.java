/**
 * 
 */
package org.epanetgrid.resultado.alarmes;

import javax.units.Dimension;

/**
 * @author alan
 *
 */
public interface IAlarme {

	public enum Tipo { max, min, warning };
	
	public Dimension getTipoDeAlarme();
	
	public Tipo getTipoDeRestricao();
	
	public int getIntervalo();
	
	public String getDescricao();
}
