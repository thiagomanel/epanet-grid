/**
 * 
 */
package org.epanetgrid.resultado.output;

import javax.units.Dimension;
import javax.units.Unit;

/**
 * @author alan
 *
 */
public interface IAlarme {
	
	public enum Tipo { max, min, warning, halted, unbalanced };
	
	public Dimension getTipoDeAlarme();
	
	public Tipo getTipoDeRestricao();
	
	public DateTimeInterval getDate();
	
	public String getDescricao();
	
}
