/**
 * 
 */
package org.epanetgrid.resultado.output;

import javax.units.Dimension;

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
