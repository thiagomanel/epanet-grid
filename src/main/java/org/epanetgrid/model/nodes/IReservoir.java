/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;

import org.epanetgrid.model.INode;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public interface IReservoir<T extends IReservoir> extends INode<T> {

	public Measure<Length> getHead();
	
	public String getHeadPatternID();
}
