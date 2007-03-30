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
public interface IValve<T extends IPipe> extends ILink<T> {

//	valve type
//	valve setting TODO:
	
	public Measure<Length> getDiameter();
	
	public Measure<Dimensionless> getLossCoefficient();
}
