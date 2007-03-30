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
public interface IPipe<T extends IPipe> extends ILink<T> {

	public static enum Status {OPEN, CLOSED, CV};
	
	public Measure<Length> getLength();
	
	public Measure<Length> getDiameter();
	
	public Measure<Dimensionless> getRoughnessCoefficient();
	
	public Measure<Dimensionless> getLossCoefficient();
	
	public Status getStatus();
	
}
