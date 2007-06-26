/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;

import org.epanetgrid.model.INode;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public interface IReservoir<T extends IReservoir> extends INode<T> {

	/**
	 * @return
	 */
	public Measure<Length> getHead();
	
	/**
	 * @return
	 */
	public String getHeadPatternID();
}
