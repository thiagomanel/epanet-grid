/**
 * 
 */
package org.epanetgrid.model.link;

import javax.quantities.Dimensionless;
import javax.quantities.Length;

import org.epanetgrid.model.ILink;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public interface IValve<T extends IValve> extends ILink<T> {

//	valve type
//	valve setting TODO:
	
	public Measure<Length> getDiameter();
	
	public Measure<Dimensionless> getLossCoefficient();
	
	public String getType();
	
	public String getSetting();
}
