/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;
import javax.quantities.Volume;

import org.epanetgrid.model.INode;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public interface ITank extends INode {

	public Measure<Length> getElevation();
	
	public Measure<Length> getInitialWaterLevel();
	
	public Measure<Length> getMinimumWaterLevel();
	
	public Measure<Length> getMaximumWaterLevel();
	
	public Measure<Length> getNominalDiameter();
	
	public Measure<Volume> getMinimuVolume();
	
	public String getVolumeCurveID();
}
