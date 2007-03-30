/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;
import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.INode;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public interface IJunction extends INode {
	
	public Measure<Length> getElevation();
	
	public Measure<VolumetricFlowRate> getBaseDemandFlow();
	
	public String getDemandPatternID();
}
