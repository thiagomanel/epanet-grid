/**
 * 
 */
package org.epanetgrid.model.nodes;

import javax.quantities.Length;
import javax.quantities.Pressure;
import javax.quantities.VolumetricFlowRate;

import org.epanetgrid.model.INode;
import org.jscience.physics.measures.Measure;


/**
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 19/06/2007
 */
public interface IJunction<T extends IJunction> extends INode<T> {
	
	/**
	 * @return
	 */
	public Measure<Length> getElevation();
	
	/**
	 * @return
	 */
	public Measure<VolumetricFlowRate> getBaseDemandFlow();
	
	/**
	 * @return
	 */
	public String getDemandPatternID();
	
	/**
	 * @return
	 */
	public Measure<Pressure> getMaxPressure();
	
	/**
	 * @return
	 */
	public Measure<Pressure> getMinPressure();

}
