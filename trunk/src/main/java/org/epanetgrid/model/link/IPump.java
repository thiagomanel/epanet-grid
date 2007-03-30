/**
 * 
 */
package org.epanetgrid.model.link;

import javax.quantities.Dimensionless;
import javax.quantities.Energy;

import org.epanetgrid.model.ILink;
import org.jscience.physics.measures.Measure;

/**
 * @author thiago
 *
 */
public interface IPump extends ILink {

	public Measure<Energy> getPower();
	
	public String getHeadCurveID();
	
	public Measure<Dimensionless> getRelativeSpeed();
	
	public String idTimePattern();
}
