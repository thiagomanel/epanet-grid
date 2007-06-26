/**
 * 
 */
package org.epanetgrid.model.link;

import javax.quantities.Dimensionless;
import javax.quantities.Energy;

import org.epanetgrid.model.ILink;
import org.jscience.physics.measures.Measure;

/**
 * @author Thiago Emmanuel Pereira, thiago.manel@gmail.com
 */
public interface IPump<T extends IPump> extends ILink<T> {

	/**
	 * @return
	 */
	public Measure<Energy> getPower();
	
	/**
	 * @return
	 */
	public String getHeadCurveID();
	
	/**
	 * @return
	 */
	public Measure<Dimensionless> getRelativeSpeed();
	
	/**
	 * @return
	 */
	public String idTimePattern();
}
